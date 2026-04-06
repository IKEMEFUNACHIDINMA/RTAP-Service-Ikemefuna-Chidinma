package com.example.rtapservice.service;

import com.example.rtapservice.dto.gemini.Content;
import com.example.rtapservice.dto.gemini.GeminiRequest;
import com.example.rtapservice.dto.gemini.Part;
import com.example.rtapservice.model.SessionEvent;
import com.example.rtapservice.repository.SessionEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiSummarizationService {

    private final SessionEventRepository eventLogRepository;
    private final RestTemplate restTemplate;

    // Pulls your API key from application.yml
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String generateSessionSummary(Long sessionId) {
        // 1. Fetch the raw events from Postgres
        List<SessionEvent> events = eventLogRepository.findBySessionIdOrderByTimestampAsc(sessionId);

        if (events.isEmpty()) {
            return "No data available for this session.";
        }

        // 2. Build the Prompt String
        StringBuilder promptText = new StringBuilder();
        promptText.append("You are an AI teaching assistant. Summarize the following live class session logs. ");
        promptText.append("Provide a brief overview of what happened, who was active, and list any action items or common questions.\n\n");
        promptText.append("Session Logs:\n");

        for (SessionEvent event : events) {
            promptText.append(event.getTimestamp())
                      .append(" - User ").append(event.getUserId())
                      .append(" [").append(event.getEventType()).append("]: ")
                      .append(event.getPayload() != null ? event.getPayload() : "")
                      .append("\n");
        }

        // 3. Construct the deeply nested JSON payload using our DTOs
        Part part = new Part(promptText.toString());
        Content content = new Content(List.of(part));
        GeminiRequest requestBody = new GeminiRequest(List.of(content));

        // 4. Set the Headers (Gemini requires Content-Type: application/json)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Combine headers and body into an HttpEntity
        HttpEntity<GeminiRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        // 5. Construct the specific URL with your API Key
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey;

        try {
            // 6. Make the POST request. We use JsonNode to easily parse the response tree.
            JsonNode response = restTemplate.postForObject(url, requestEntity, JsonNode.class);

            // 7. Navigate the response JSON tree to extract just the AI's generated text
            if (response != null && response.has("candidates")) {
                return response.get("candidates").get(0)
                               .get("content")
                               .get("parts").get(0)
                               .get("text").asText();
            }
            return "Failed to generate summary: Invalid response from AI.";

        } catch (Exception e) {
            return "Error calling AI API: " + e.getMessage();
        }
    }
}