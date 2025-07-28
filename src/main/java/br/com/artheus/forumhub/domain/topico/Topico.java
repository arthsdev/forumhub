package br.com.artheus.forumhub.domain.topico;

import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode ficar em branco")
    @Size(min = 8, max = 30, message = "Titulo deve conter entre 8 e 30 caracteres")
    private String titulo;

    @NotBlank(message = "Mensagem não pode ficar em branco")
    @Size(min = 50, max = 1000, message = "Mensagem deve ter entre 50 e 1000 caracteres")
    private String mensagem;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @NotNull(message = "Autor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Topico(String titulo, String mensagem, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
    }
}

