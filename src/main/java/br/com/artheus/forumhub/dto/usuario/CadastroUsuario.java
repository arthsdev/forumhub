package br.com.artheus.forumhub.dto.usuario;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroUsuario(

        @NotBlank(message = "Nome não pode estar em branco!")
        @Size(min = 3, max = 50)
        String nome,

        @NotBlank(message = "Email não pode estar em branco!")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha não pode estar em branco!")
        @Size(min = 6, max = 20)
        String senha
) {

        public Usuario toEntity(){
                return new Usuario(nome, email, senha);
        }


}
