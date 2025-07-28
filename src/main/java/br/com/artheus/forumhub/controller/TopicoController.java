package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.topico.AtualizacaoTopico;
import br.com.artheus.forumhub.dto.topico.CadastroTopico;
import br.com.artheus.forumhub.dto.topico.DetalhamentoTopico;
import br.com.artheus.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;


    @PostMapping
    public ResponseEntity<DetalhamentoTopico> criarTopico(@RequestBody @Valid CadastroTopico dados) {

        var topico = topicoService.criarTopico(dados);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topico.id())
                .toUri();

        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public ResponseEntity<List<DetalhamentoTopico>> listarTopicos() {
        var topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoTopico> buscarTopicoPorId(@PathVariable Long id) {

        var topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok(DetalhamentoTopico.fromEntity(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoTopico> atualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid AtualizacaoTopico dados
            ){
        var topicoAtualizado = topicoService.atualizarTopico(id, dados);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id){
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
