package br.com.artheus.forumhub.dto.usuario;

import br.com.artheus.forumhub.domain.usuario.Usuario;

public record DetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        boolean ativo
) {

    public static DetalhamentoUsuario fromEntity(Usuario usuario) {
        return new DetalhamentoUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.isAtivo()
        );
    }
}
