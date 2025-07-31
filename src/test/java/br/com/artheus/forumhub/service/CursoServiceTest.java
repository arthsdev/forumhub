package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.curso.Categoria;
import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.repository.CursoRepository;
import br.com.artheus.forumhub.repository.TopicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CursoServiceTest {

    private CursoRepository cursoRepository;
    private TopicoRepository topicoRepository;
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        cursoRepository = mock(CursoRepository.class);
        topicoRepository = mock(TopicoRepository.class);
        cursoService = new CursoService(cursoRepository, topicoRepository);
    }

    @Test
    void deveCriarCursoComSucesso() {
        CadastroCurso cadastro = new CadastroCurso("Java Básico", Categoria.BACKEND);
        Curso cursoEntity = cadastro.toEntity();

        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoEntity);

        DetalhamentoCurso result = cursoService.criarCurso(cadastro);

        assertEquals("Java Básico", result.nome());
        assertEquals("BACKEND", result.categoria().name());
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    void deveListarTodosOsCursos() {
        Curso curso1 = new Curso(1L, "Java", Categoria.BACKEND);
        Curso curso2 = new Curso(2L, "Python", Categoria.DATA_SCIENCE);

        when(cursoRepository.findAll()).thenReturn(List.of(curso1, curso2));

        List<DetalhamentoCurso> result = cursoService.listarCursos();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).nome());
        assertEquals("Python", result.get(1).nome());
    }

    @Test
    void deveBuscarCursosPorNome() {
        Curso curso = new Curso(1L, "Java", Categoria.BACKEND);

        when(cursoRepository.findByNomeContainingIgnoreCase("java")).thenReturn(List.of(curso));

        List<DetalhamentoCurso> resultado = cursoService.buscarPorNome("java");

        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).nome());
    }

    @Test
    void deveBuscarCursoPorId() {
        Curso curso = new Curso(1L, "Spring", Categoria.BACKEND);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Spring", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoCursoNaoEncontradoAoBuscarPorId() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cursoService.buscarPorId(99L));

        assertEquals("Curso não encontrado", exception.getMessage());
    }

    @Test
    void deveDeletarCursoComSucesso() {
        when(cursoRepository.existsById(1L)).thenReturn(true);
        when(topicoRepository.existsByCursoId(1L)).thenReturn(false);

        cursoService.deletarCurso(1L);

        verify(cursoRepository).deleteById(1L);
    }

    @Test
    void naoDeveDeletarCursoSeNaoExistir() {
        when(cursoRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cursoService.deletarCurso(99L));

        assertEquals("Curso não encontrado", exception.getMessage());
        verify(cursoRepository, never()).deleteById(anyLong());
    }

    @Test
    void naoDeveDeletarCursoSePossuirTopicos() {
        when(cursoRepository.existsById(1L)).thenReturn(true);
        when(topicoRepository.existsByCursoId(1L)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cursoService.deletarCurso(1L));

        assertEquals("Não é possível deletar curso que possui tópicos associados", exception.getMessage());
        verify(cursoRepository, never()).deleteById(anyLong());
    }
}
