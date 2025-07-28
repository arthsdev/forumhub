package br.com.artheus.forumhub.repository;

import br.com.artheus.forumhub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByNomeContainingIgnoreCase(String nome);
}
