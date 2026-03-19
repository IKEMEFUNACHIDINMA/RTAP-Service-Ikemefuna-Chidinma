package com.example.rtapservice.controller;

import com.example.rtapservice.dto.JoinRequest;
import com.example.rtapservice.model.SessionEvent;
import com.example.rtapservice.repository.SessionEventRepository;
import com.example.rtapservice.service.PresenceCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final PresenceCacheService cacheService;
    private final SessionEventRepository eventRepository;

    @MessageMapping("/session/{sessionId}/join")
    @SendTo("/topic/session/{sessionId}")
    public SessionEvent handleJoin(@DestinationVariable Long sessionId, @Payload JoinRequest request) {
        cacheService.markUserPresent(sessionId, request.getUserId());

        SessionEvent event = new SessionEvent(sessionId, request.getUserId(), "JOIN", "User joined");
        eventRepository.save(event); // Persist to Postgres

        return event; // Broadcast to all clients on this topic
    }

    @MessageMapping("/session/{sessionId}/leave")
    @SendTo("/topic/session/{sessionId}")
    public SessionEvent handleLeave(@DestinationVariable Long sessionId, @Payload JoinRequest request) {
        cacheService.markUserAbsent(sessionId, request.getUserId());

        SessionEvent event = new SessionEvent(sessionId, request.getUserId(), "LEAVE", "User left");
        eventRepository.save(event);

        return event;
    }
}
