package br.com.artheus.forumhub.dto.topico;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.domain.topico.StatusTopico;
import br.com.artheus.forumhub.domain.topico.Topico;
import br.com.artheus.forumhub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        usuarioDetalhamento autor,
        CursoDetalhamento curso
) {

    // DTO pro Usuario, so expoe campos publicos
    public record usuarioDetalhamento(Long id, String nome, String email){
        public static usuarioDetalhamento fromEntity(Usuario usuario){
            return new usuarioDetalhamento(usuario.getId(), usuario.getNome(), usuario.getEmail());
        }
    }

    // DTO pro Curso, so expoe campos publicos
    public record CursoDetalhamento(Long id, String nome){
        public static CursoDetalhamento fromEntity(Curso curso){
            return new CursoDetalhamento(curso.getId(), curso.getNome());
        }
    }

    // Usamos para converter entidade Topico para DTO DetalhamentoTopico
    public static DetalhamentoTopico fromEntity(Topico topico) {
        return new DetalhamentoTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                usuarioDetalhamento.fromEntity(topico.getAutor()),
                CursoDetalhamento.fromEntity(topico.getCurso())
        );
    }


}
