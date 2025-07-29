package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza a autenticação do usuário e retorna um token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtService.gerarToken(usuario.getUsername());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Data
    public static class AuthRequest {
        @Schema(description = "E-mail do usuário", example = "usuario@email.com")
        private String email;

        @Schema(description = "Senha do usuário", example = "senha123")
        private String senha;
    }

    @Data
    public static class TokenResponse {
        @Schema(description = "Token JWT gerado para autenticação", example = "eyJhbGciOiJIUzI1...")
        private final String token;
    }
}
