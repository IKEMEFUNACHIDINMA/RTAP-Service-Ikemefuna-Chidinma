package com.example.rtapservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthRequest(
        @Schema(example = "prof_smith", description = "The username of the user trying to log in")
        String username
) {}
