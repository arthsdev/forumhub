package br.com.artheus.forumhub.controller;

import br.com.artheus.forumhub.dto.usuario.CadastroUsuario;
import br.com.artheus.forumhub.dto.usuario.DetalhamentoUsuario;
import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.service.UsuarioService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        CadastroUsuario cadastro = new CadastroUsuario("Fulano", "fulano@email.com", "123456");

        Usuario usuario = new Usuario(1L, "Fulano", "fulano@email.com", "123456");
        usuario.setAtivo(true);

        DetalhamentoUsuario detalhamento = DetalhamentoUsuario.fromEntity(usuario);

        when(usuarioService.criarUsuario(any(CadastroUsuario.class))).thenReturn(detalhamento);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastro)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Fulano"))
                .andExpect(jsonPath("$.email").value("fulano@email.com"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void deveListarUsuariosComSucesso() throws Exception {
        Usuario usuario1 = new Usuario(1L, "Fulano", "fulano@email.com", "123456");
        usuario1.setAtivo(true);
        Usuario usuario2 = new Usuario(2L, "Beltrano", "beltrano@email.com", "123456");
        usuario2.setAtivo(false);

        List<DetalhamentoUsuario> lista = List.of(
                DetalhamentoUsuario.fromEntity(usuario1),
                DetalhamentoUsuario.fromEntity(usuario2)
        );

        when(usuarioService.listarUsuarios()).thenReturn(lista);

        mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Fulano"))
                .andExpect(jsonPath("$[1].ativo").value(false));
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Fulano", "fulano@email.com", "123456");
        usuario.setAtivo(true);

        when(usuarioService.buscarPorId(id)).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Fulano"))
                .andExpect(jsonPath("$.email").value("fulano@email.com"));
    }

    @Test
    void deveInativarUsuarioComSucesso() throws Exception {
        Long id = 1L;

        doNothing().when(usuarioService).inativarUsuario(id);

        mockMvc.perform(delete("/usuarios/{id}", id))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).inativarUsuario(id);
    }
}
