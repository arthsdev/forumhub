package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.topico.CadastroTopico;
import br.com.artheus.forumhub.dto.topico.DetalhamentoTopico;
import br.com.artheus.forumhub.domain.topico.StatusTopico;
import br.com.artheus.forumhub.service.TopicoService;
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

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TopicoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TopicoService topicoService;

    @InjectMocks
    private TopicoController topicoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(topicoController).build();
    }

    @Test
    void deveCriarTopicoComSucesso() throws Exception {
        CadastroTopico cadastro = new CadastroTopico(
                "Título válido do tópico",
                "Mensagem válida com mais de 50 caracteres para passar na validação do campo mensagem.",
                1L,
                1L
        );

        DetalhamentoTopico detalhamento = new DetalhamentoTopico(
                1L,
                cadastro.titulo(),
                cadastro.mensagem(),
                LocalDateTime.now(),
                StatusTopico.NAO_RESPONDIDO,
                new DetalhamentoTopico.usuarioDetalhamento(1L, "Autor Teste", "autor@teste.com"),
                new DetalhamentoTopico.CursoDetalhamento(1L, "Curso Teste")
        );

        when(topicoService.criarTopico(any(CadastroTopico.class))).thenReturn(detalhamento);

        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastro)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/topicos/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value(cadastro.titulo()))
                .andExpect(jsonPath("$.mensagem").value(cadastro.mensagem()));
    }
}
