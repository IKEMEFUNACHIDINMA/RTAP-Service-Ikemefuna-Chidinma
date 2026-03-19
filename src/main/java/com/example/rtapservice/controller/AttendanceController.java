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
public class AttendanceController {

    private final PresenceCacheService cacheService;
    private final SessionEventRepository eventLogRepository; // Standard Spring Data JPA Repo

    @MessageMapping("/session/{sessionId}/join")
    @SendTo("/topic/session/{sessionId}")
    public SessionEvent handleJoin(@DestinationVariable Long sessionId, @Payload JoinRequest request) {
        // 1. Update Cache
        cacheService.markUserPresent(sessionId, request.getUserId());

        // 2. Persist Event to DB asynchronously or directly
        SessionEvent event = new SessionEvent(sessionId, request.getUserId(), "JOIN", null);
        eventLogRepository.save(event);

        // 3. Broadcast to all subscribers
        return event;
    }

    // Implement similar mappings for /leave and /raise-hand
}
