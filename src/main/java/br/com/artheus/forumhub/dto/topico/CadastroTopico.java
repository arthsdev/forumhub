package br.com.artheus.forumhub.dto.topico;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.domain.topico.Topico;
import br.com.artheus.forumhub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroTopico(

        @NotBlank(message = "Titulo não pode estar em branco")
        @Size(min = 8, max = 30, message = "Deve conter entre 8 e 30 caracteres")
        String titulo,

        @NotBlank(message = "Mensagem não pode estar em branco")
        @Size(min = 50, max = 1000, message = "Deve conter entre 50 e 1000 caracteres")
        String mensagem,

        @NotNull(message = "ID do autor não pode estar em branco")
        @Positive(message = "ID do autor deve ser maior que zero")
        Long autorId,

        @NotNull(message = "ID do curso não pode estar em branco")
        @Positive(message = "ID do curso deve ser maior que zero")
        Long cursoId
) {

        public Topico toEntity(Usuario autor, Curso curso) {
                return new Topico(titulo, mensagem, autor, curso);
        }
}
