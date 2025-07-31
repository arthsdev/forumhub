package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.usuario.Usuario;
import br.com.artheus.forumhub.dto.usuario.CadastroUsuario;
import br.com.artheus.forumhub.dto.usuario.DetalhamentoUsuario;
import br.com.artheus.forumhub.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarUsuarioComSenhaCriptografada() {
        var dados = new CadastroUsuario("Fulano", "fulano@email.com", "123456");
        var usuarioOriginal = dados.toEntity();

        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DetalhamentoUsuario resultado = usuarioService.criarUsuario(dados);

        assertEquals("Fulano", resultado.nome());
        assertEquals("fulano@email.com", resultado.email());
        // A senha original não aparece no DTO, mas podemos verificar internamente se foi criptografada:
        verify(passwordEncoder, times(1)).encode("123456");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveListarTodosUsuarios() {
        var usuario1 = new Usuario("João", "joao@email.com", "senha1");
        var usuario2 = new Usuario("Maria", "maria@email.com", "senha2");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        List<DetalhamentoUsuario> lista = usuarioService.listarUsuarios();

        assertEquals(2, lista.size());
        assertEquals("João", lista.get(0).nome());
        assertEquals("Maria", lista.get(1).nome());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarUsuarioPorIdQuandoExiste() {
        var usuario = new Usuario("Ana", "ana@email.com", "senha");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertEquals("Ana", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoBuscarUsuarioPorIdInexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.buscarPorId(999L);
        });

        assertEquals("Usuario não encontrado!", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    void deveInativarUsuarioQuandoExiste() {
        var usuario = new Usuario("Carlos", "carlos@email.com", "senha");
        usuario.setAtivo(true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.inativarUsuario(1L);

        assertFalse(usuario.isAtivo());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deveLancarExcecaoAoInativarUsuarioInexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.inativarUsuario(999L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(999L);
    }
}
