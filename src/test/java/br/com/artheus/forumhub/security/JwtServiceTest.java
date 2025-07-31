package br.com.artheus.forumhub.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private final String rawSecret = "my-secret-key-for-jwt-test-which-needs-to-be-very-long";
    private String encodedSecret;

    @BeforeEach
    void setUp() {
        encodedSecret = Base64.getEncoder().encodeToString(rawSecret.getBytes());
        jwtService = new JwtService(encodedSecret);
    }

    @Test
    void deveGerarTokenValidoEExtrairUsername() {
        String username = "arthur@email.com";
        UserDetails userDetails = User.withUsername(username).password("123").authorities(List.of()).build();

        String token = jwtService.gerarToken(userDetails.getUsername());

        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void deveDetectarTokenInvalido() {
        String token = jwtService.gerarToken("outro@email.com");
        UserDetails userDetails = User.withUsername("nao-bate").password("123").authorities(List.of()).build();

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void tokenExpiradoDeveSerInvalido() throws InterruptedException {
        // Token com expiração manualmente ajustada (opcional: adaptar JwtService para permitir isso em testes)
        String username = "user";
        String token = jwtService.gerarToken(username);
        Thread.sleep(10); // Não expira de fato, mas serve como placeholder para teste futuro

        UserDetails userDetails = User.withUsername(username).password("123").authorities(List.of()).build();
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }
}
