package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.dto.topico.DetalhamentoTopico;
import br.com.artheus.forumhub.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<DetalhamentoCurso> criarCurso(@RequestBody @Valid CadastroCurso dados) {
        var curso = cursoService.criarCurso(dados);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.id())
                .toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping
    public ResponseEntity<List<DetalhamentoCurso>> listarCursos() {
        var cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCurso> buscarCursoPorId(@PathVariable Long id) {
        var curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(DetalhamentoCurso.fromEntity(curso));
    }
}
