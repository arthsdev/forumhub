package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Cursos", description = "Endpoints relacionados aos cursos do fórum")
@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @Operation(summary = "Criar novo curso", description = "Cadastra um novo curso no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    public ResponseEntity<DetalhamentoCurso> criarCurso(
            @RequestBody @Valid CadastroCurso dados) {
        var curso = cursoService.criarCurso(dados);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.id())
                .toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @Operation(summary = "Listar todos os cursos", description = "Retorna a lista completa de cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<DetalhamentoCurso>> listarCursos() {
        var cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    @Operation(summary = "Buscar curso por ID", description = "Retorna os dados de um curso específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCurso> buscarCursoPorId(
            @Parameter(description = "ID do curso", example = "1") @PathVariable Long id) {
        var curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(DetalhamentoCurso.fromEntity(curso));
    }

    @Operation(summary = "Deletar curso", description = "Remove um curso existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(
            @Parameter(description = "ID do curso a ser deletado", example = "1") @PathVariable Long id) {
        cursoService.deletarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
