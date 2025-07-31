package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.domain.curso.Categoria;
import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CursoControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CursoService cursoService;

    @InjectMocks
    private CursoController cursoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cursoController).build();
    }

    @Test
    void deveCriarCursoComSucesso() throws Exception {
        CadastroCurso cadastro = new CadastroCurso("Spring Boot", Categoria.BACKEND);
        DetalhamentoCurso detalhado = new DetalhamentoCurso(1L, "Spring Boot", Categoria.BACKEND);

        when(cursoService.criarCurso(any(CadastroCurso.class))).thenReturn(detalhado);

        mockMvc.perform(post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastro)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/cursos/1")))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Spring Boot"));
    }

    @Test
    void deveListarCursosComSucesso() throws Exception {
        List<DetalhamentoCurso> lista = List.of(
                new DetalhamentoCurso(1L, "Java", Categoria.BACKEND),
                new DetalhamentoCurso(2L, "Spring", Categoria.BACKEND)
        );

        when(cursoService.listarCursos()).thenReturn(lista);

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Java"))
                .andExpect(jsonPath("$[1].nome").value("Spring"));
    }

    @Test
    void deveBuscarCursoPorIdComSucesso() throws Exception {
        Curso curso = new Curso(1L, "Segurança da Informação", Categoria.SEGURANCA);

        when(cursoService.buscarPorId(1L)).thenReturn(curso);

        mockMvc.perform(get("/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Segurança da Informação"));
    }

    @Test
    void deveDeletarCursoComSucesso() throws Exception {
        doNothing().when(cursoService).deletarCurso(1L);

        mockMvc.perform(delete("/cursos/1"))
                .andExpect(status().isNoContent());

        verify(cursoService, times(1)).deletarCurso(1L);
    }
}
