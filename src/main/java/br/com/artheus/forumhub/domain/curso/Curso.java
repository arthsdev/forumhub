package br.com.artheus.forumhub.domain.curso;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do curso é obrigatório")
    private String nome;

    @NotNull(message = "Categoria do curso é obrigatória")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

}
