package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.usuario.CadastroUsuario;
import br.com.artheus.forumhub.dto.usuario.DetalhamentoUsuario;
import br.com.artheus.forumhub.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DetalhamentoUsuario> criarUsuario(@RequestBody @Valid CadastroUsuario dados){

        var usuario = usuarioService.criarUsuario(dados);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.id())
                .toUri();
        return ResponseEntity.created(uri).body(usuario);
    }


    @GetMapping
    public ResponseEntity<List<DetalhamentoUsuario>> listarUsuarios(){

        var usuarios = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoUsuario> buscarUsuarioPorId(@PathVariable Long id){
        var usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(DetalhamentoUsuario.fromEntity(usuario));
    }
}
