package com.example.rtapservice.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for incoming WebSocket messages when a user joins or leaves a session.
 */
public record JoinRequest(

        @NotNull(message = "User ID must be provided")
        Long userId

) {// Custom getter to satisfy libraries/IDEs that expect standard JavaBeans naming
    public Long getUserId() {
        return this.userId;
    }}
