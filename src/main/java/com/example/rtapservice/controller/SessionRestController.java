package com.example.rtapservice.controller;

import com.example.rtapservice.service.AiSummarizationService;
import com.example.rtapservice.service.PresenceCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Session Management", description = "Endpoints for managing active class sessions and AI summaries")
public class SessionRestController {

    private final PresenceCacheService presenceService;
    private final AiSummarizationService aiService;

    // Get real-time active users from Cache
    @Operation(summary = "Get active users", description = "Retrieves a set of user IDs currently active in a specific session from the cache.")
    @GetMapping("/{sessionId}/active-users")
    public ResponseEntity<Set<Long>> getActiveUsers(
            @Parameter(description = "The ID of the class session") @PathVariable Long sessionId) {
        return ResponseEntity.ok(presenceService.getActiveUsers(sessionId));
    }

    // Trigger AI Summary
    @Operation(summary = "Generate AI Summary", description = "Fetches session chat logs and calls the AI API to generate a concise summary and action items.")
    @PostMapping("/{sessionId}/summarize")
    public ResponseEntity<String> generateSummary(@Parameter(description = "The ID of the class session to summarize") @PathVariable Long sessionId) {
        String summary = aiService.generateSessionSummary(sessionId);
        return ResponseEntity.ok(summary);
    }
}
