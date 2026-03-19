package com.example.rtapservice.dto.gemini;

import java.util.List;

public record GeminiRequest(
        List<Content> contents) {

}