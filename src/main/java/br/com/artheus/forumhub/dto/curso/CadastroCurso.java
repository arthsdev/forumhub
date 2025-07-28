package br.com.artheus.forumhub.dto.curso;

import br.com.artheus.forumhub.domain.curso.Categoria;
import br.com.artheus.forumhub.domain.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroCurso(

        @NotBlank(message = "Nome não pode estar em branco!")
        String nome,

        @NotNull(message = "Categoria não pode estar em branco!")
        Categoria categoria
) {

    public Curso toEntity() {
        return new Curso(null, nome, categoria);
    }
}
