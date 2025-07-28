package br.com.artheus.forumhub.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizacaoTopico(

        @NotBlank(message = "Titulo não pode estar em branco")
        @Size(min = 8, max = 30)
        String titulo,

        @NotBlank(message = "Mensagem não pode estar em branco!")
        @Size(min = 50, max = 1000)
        String mensagem
) {
}
