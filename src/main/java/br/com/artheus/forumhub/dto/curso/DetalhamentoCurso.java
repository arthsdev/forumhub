package br.com.artheus.forumhub.dto.curso;

import br.com.artheus.forumhub.domain.curso.Curso;

public record DetalhamentoCurso(
        Long id,
        String nome
) {

    public static DetalhamentoCurso fromEntity(Curso curso){
        return new DetalhamentoCurso(
                curso.getId(),
                curso.getNome()
        );
    }
}


//TODO: TERMINAR CONTROLLERS FALTANDO!!!!