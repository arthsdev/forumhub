package br.com.artheus.forumhub.integrationtest;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e buscar um usuário pelo ID")
    void deveSalvarEBuscarUsuario() {
        Usuario usuario = new Usuario("Teste Usuário", "teste@email.com", "senha123");

        Usuario salvo = usuarioRepository.save(usuario);
        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Teste Usuário");
        assertThat(encontrado.get().getEmail()).isEqualTo("teste@email.com");
        assertThat(encontrado.get().getSenha()).isEqualTo("senha123");
        assertThat(encontrado.get().isAtivo()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar usuário inexistente")
    void deveRetornarVazioParaUsuarioInexistente() {
        Optional<Usuario> resultado = usuarioRepository.findById(-1L);
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todos os usuários salvos")
    void deveListarTodosUsuarios() {
        Usuario u1 = new Usuario("Usuário 1", "u1@email.com", "senha1");
        Usuario u2 = new Usuario("Usuário 2", "u2@email.com", "senha2");
        usuarioRepository.save(u1);
        usuarioRepository.save(u2);

        var lista = usuarioRepository.findAll();

        assertThat(lista).hasSize(2);
        assertThat(lista).extracting("email").containsExactlyInAnyOrder("u1@email.com", "u2@email.com");
    }
}
