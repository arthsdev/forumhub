package br.com.artheus.forumhub.e2e;

import br.com.artheus.forumhub.dto.auth.AuthRequest;
import br.com.artheus.forumhub.dto.auth.TokenResponse;
import br.com.artheus.forumhub.dto.usuario.CadastroUsuario;
import br.com.artheus.forumhub.dto.usuario.DetalhamentoUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String endpoint) {
        return "http://localhost:" + port + endpoint;
    }

    @Test
    public void fluxoCompletoCadastroLoginEBuscaUsuario() {
        String baseUrl = "http://localhost:" + port;

        // 1 - Cadastro do usuário
        CadastroUsuario cadastro = new CadastroUsuario("abacate", "abacate@fruta.com", "123456");
        ResponseEntity<Void> responseCadastro = restTemplate.postForEntity(baseUrl + "/usuarios", cadastro, Void.class);
        assertEquals(HttpStatus.CREATED, responseCadastro.getStatusCode());

        // 2 - Login para obter token
        AuthRequest login = new AuthRequest();
        login.setEmail("abacate@fruta.com");
        login.setSenha("123456");

        ResponseEntity<TokenResponse> responseLogin = restTemplate.postForEntity(baseUrl + "/auth/login", login, TokenResponse.class);

        assertEquals(HttpStatus.OK, responseLogin.getStatusCode());

        assertNotNull(responseLogin.getBody(), "TokenResponse body é nulo");
        String token = responseLogin.getBody().token();
        System.out.println("Token JWT obtido no login: " + token);
        assertNotNull(token, "Token JWT é nulo");

        // 3 - Acessar /usuarios/me com token no header Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<DetalhamentoUsuario> responseMe = restTemplate.exchange(
                baseUrl + "/usuarios/me",
                HttpMethod.GET,
                entity,
                DetalhamentoUsuario.class
        );

        assertEquals(HttpStatus.OK, responseMe.getStatusCode(), "Resposta /usuarios/me não foi OK");
        assertNotNull(responseMe.getBody(), "Body da resposta /usuarios/me é nulo");

        DetalhamentoUsuario usuarioLogado = responseMe.getBody();
        assertEquals("abacate", usuarioLogado.nome(), "Nome do usuário logado está incorreto");
        assertEquals("abacate@fruta.com", usuarioLogado.email(), "Email do usuário logado está incorreto");
    }
}


