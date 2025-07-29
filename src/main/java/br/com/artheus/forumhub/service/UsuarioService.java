package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.dto.usuario.CadastroUsuario;
import br.com.artheus.forumhub.dto.usuario.DetalhamentoUsuario;
import br.com.artheus.forumhub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public DetalhamentoUsuario criarUsuario(CadastroUsuario dados){
        Usuario usuario = usuarioRepository.save(dados.toEntity());
        return DetalhamentoUsuario.fromEntity(usuario);
    }

    public List<DetalhamentoUsuario> listarUsuarios(){
        return usuarioRepository.findAll().stream()
                .map(DetalhamentoUsuario::fromEntity)
                .collect(Collectors.toList());
    }


    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado!"));
    }

    @Transactional
    public void inativarUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
