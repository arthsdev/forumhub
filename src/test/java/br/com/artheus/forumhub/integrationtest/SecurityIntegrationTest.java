package br.com.artheus.forumhub.integrationtest;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        // Remove o usuário se já existir para evitar conflito
        usuarioRepository.findByEmail("admin@teste.com").ifPresent(usuarioRepository::delete);

        // Cria um usuário novo com senha criptografada
        Usuario admin = new Usuario();
        admin.setEmail("admin@teste.com");
        admin.setSenha(passwordEncoder.encode("123456"));
        admin.setNome("Admin Teste");
        // ex: admin.setNome("Admin Teste");

        usuarioRepository.save(admin);
    }

    @Test
    void deveRetornar401AoAcessarEndpointProtegidoSemToken() throws Exception {
        mockMvc.perform(get("/topicos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarTokenAoFazerLoginComCredenciaisValidas() throws Exception {
        String body = """
            {
                "email": "admin@teste.com",
                "senha": "123456"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void deveNegarLoginComCredenciaisInvalidas() throws Exception {
        String body = """
            {
                "email": "admin@teste.com",
                "senha": "senhaerrada"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }
}
