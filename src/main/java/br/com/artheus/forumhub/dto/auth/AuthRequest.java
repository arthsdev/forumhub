package br.com.artheus.forumhub.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @Schema(description = "E-mail do usuário", example = "usuario@email.com")
    @NotBlank
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank
    private String senha;
}
