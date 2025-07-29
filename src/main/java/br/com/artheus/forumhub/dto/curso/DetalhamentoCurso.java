package br.com.artheus.forumhub.dto.curso;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.domain.curso.Categoria;

public record DetalhamentoCurso(
        Long id,
        String nome,
        Categoria categoria
) {

    public static DetalhamentoCurso fromEntity(Curso curso){
        return new DetalhamentoCurso(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria()
        );
    }
}
