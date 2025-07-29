package br.com.artheus.forumhub.repository;


import br.com.artheus.forumhub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);
    boolean existsByTituloAndMensagemAndIdNot(String titulo, String mensagem, Long id);
    boolean existsByCursoId(Long cursoId);

}
