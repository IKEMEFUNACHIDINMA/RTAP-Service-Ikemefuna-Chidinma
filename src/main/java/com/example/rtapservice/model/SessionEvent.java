package com.example.rtapservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_events")
@Data
@NoArgsConstructor
public class SessionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;
    private Long userId;
    private String eventType; // "JOIN", "LEAVE", "RAISE_HAND"
    private String payload;
    private LocalDateTime timestamp = LocalDateTime.now();

    public SessionEvent(Long sessionId, Long userId, String eventType, String payload) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.eventType = eventType;
        this.payload = payload;
    }
}
