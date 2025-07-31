package br.com.artheus.forumhub.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(
        @Schema(description = "Token JWT gerado", example = "eyJhbGciOiJIUzI1...")
        String token
) {}
