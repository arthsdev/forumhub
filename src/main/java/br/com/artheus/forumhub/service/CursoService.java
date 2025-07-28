package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class CursoService {

    private final CursoRepository cursoRepository;

    public DetalhamentoCurso criarCurso(CadastroCurso dados){
        Curso curso = cursoRepository.save(dados.toEntity());
        return DetalhamentoCurso.fromEntity(curso);
    }

    public List<DetalhamentoCurso> listarCursos(){
        return cursoRepository.findAll().stream()
                .map(DetalhamentoCurso::fromEntity)
                .collect(Collectors.toList());
    }

    public List<DetalhamentoCurso> buscarPorNome(String nome) {
        return cursoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(DetalhamentoCurso::fromEntity)
                .collect(Collectors.toList());
    }

    public Curso buscarPorId(Long id){
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado!"));
    }
}
