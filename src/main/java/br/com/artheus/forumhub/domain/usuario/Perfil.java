package br.com.artheus.forumhub.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "perfis")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "usuarios")
public class Perfil implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do perfil não pode estar em branco")
    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @ManyToMany(mappedBy = "perfis")
    private Set<Usuario> usuarios = new HashSet<>();

    @Override
    public String getAuthority() {
        return nome;
    }
}
