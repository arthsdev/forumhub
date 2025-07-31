package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.topico.AtualizacaoParcialTopico;
import br.com.artheus.forumhub.dto.topico.AtualizacaoTopico;
import br.com.artheus.forumhub.dto.topico.CadastroTopico;
import br.com.artheus.forumhub.dto.topico.DetalhamentoTopico;
import br.com.artheus.forumhub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Tópicos", description = "Endpoints relacionados aos tópicos do fórum")
@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;

    @PostMapping
    @Operation(summary = "Cadastra um novo tópico", description = "Cria um novo tópico a partir dos dados enviados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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
    @Operation(summary = "Lista todos os tópicos", description = "Retorna uma lista com os tópicos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<DetalhamentoTopico>> listarTopicos() {
        var topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um tópico por ID", description = "Retorna os dados detalhados de um tópico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<DetalhamentoTopico> buscarTopicoPorId(@PathVariable Long id) {
        var topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok(DetalhamentoTopico.fromEntity(topico));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tópico por completo", description = "Substitui todos os dados do tópico pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DetalhamentoTopico> atualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid AtualizacaoTopico dados
    ) {
        var topicoAtualizado = topicoService.atualizarTopico(id, dados);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um tópico", description = "Altera apenas os campos informados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico atualizado parcialmente"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<DetalhamentoTopico> atualizarParcial(
            @PathVariable Long id,
            @RequestBody AtualizacaoParcialTopico dados
    ) {
        var topicoAtualizado = topicoService.atualizarParcial(id, dados);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um tópico", description = "Remove um tópico existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tópico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }
}

