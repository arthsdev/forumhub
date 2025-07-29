package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.repository.CursoRepository;
import br.com.artheus.forumhub.repository.TopicoRepository;
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
    private final TopicoRepository topicoRepository;

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

    @Transactional
    public void deletarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado");
        }

        boolean temTopicos = topicoRepository.existsByCursoId(id);
        if (temTopicos) {
            throw new IllegalArgumentException("Não é possível deletar curso que possui tópicos associados");
        }

        cursoRepository.deleteById(id);
    }


}
