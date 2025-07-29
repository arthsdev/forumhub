package br.com.artheus.forumhub.service;

import br.com.artheus.forumhub.domain.topico.StatusTopico;
import br.com.artheus.forumhub.domain.topico.Topico;
import br.com.artheus.forumhub.dto.topico.AtualizacaoParcialTopico;
import br.com.artheus.forumhub.dto.topico.AtualizacaoTopico;
import br.com.artheus.forumhub.dto.topico.CadastroTopico;
import br.com.artheus.forumhub.dto.topico.DetalhamentoTopico;
import br.com.artheus.forumhub.repository.CursoRepository;
import br.com.artheus.forumhub.repository.TopicoRepository;
import br.com.artheus.forumhub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;


    public DetalhamentoTopico criarTopico(CadastroTopico dados){
        var autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado!"));
        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado!"));

        Topico topico = dados.toEntity(autor, curso);
        topico.setStatus(StatusTopico.NAO_RESPONDIDO);
        topico = topicoRepository.save(topico);
        return DetalhamentoTopico.fromEntity(topico);
    }


    public List<DetalhamentoTopico> listarTopicos() {
        return topicoRepository.findAll().stream()
                .map(DetalhamentoTopico::fromEntity)
                .toList();
    }

    public Topico buscarPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado!"));
    }

    public DetalhamentoTopico atualizarTopico(Long id, AtualizacaoTopico dados){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado!"));

        boolean existeDuplicado = topicoRepository.existsByTituloAndMensagemAndIdNot(
                dados.titulo(), dados.mensagem(), id);

        if (existeDuplicado) {
            throw new IllegalArgumentException("Já existe um tópico com esse título e mensagem.");
        }

        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());

        topico = topicoRepository.save(topico);
        return DetalhamentoTopico.fromEntity(topico);
    }


    public void deletarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tópico não encontrado");
        }
        topicoRepository.deleteById(id);
    }

    @Transactional
    public DetalhamentoTopico atualizarParcial(Long id, AtualizacaoParcialTopico dados) {
        var topico = topicoRepository.getReferenceById(id);

        if (dados.titulo() != null && !dados.titulo().isBlank()) {
            topico.setTitulo(dados.titulo());
        }

        if (dados.mensagem() != null && !dados.mensagem().isBlank()) {
            topico.setMensagem(dados.mensagem());
        }

        return DetalhamentoTopico.fromEntity(topico);
    }
}
