package br.com.artheus.forumhub.repository;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
