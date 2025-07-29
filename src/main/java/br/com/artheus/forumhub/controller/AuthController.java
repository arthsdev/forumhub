package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.security.JwtService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



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
        private String email;
        private String senha;
    }

    @Data
    public static class TokenResponse {
        private final String token;
    }
}
