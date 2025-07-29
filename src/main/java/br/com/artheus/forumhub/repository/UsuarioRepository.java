package br.com.artheus.forumhub.repository;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByAtivoTrue();
    Optional<Usuario> findByIdAndAtivoTrue(Long id);
    Optional<Usuario> findByEmail(String email);
}
