package br.com.artheus.forumhub.integrationtest;

import br.com.artheus.forumhub.domain.curso.Categoria;
import br.com.artheus.forumhub.domain.curso.Curso;
import br.com.artheus.forumhub.dto.curso.CadastroCurso;
import br.com.artheus.forumhub.dto.curso.DetalhamentoCurso;
import br.com.artheus.forumhub.repository.CursoRepository;
import br.com.artheus.forumhub.service.CursoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class
CursoServiceIntegrationTest {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void criarCurso_deveSalvarCursoEretornarDetalhamento() {
        var cadastro = new CadastroCurso("Java Backend", Categoria.BACKEND);

        DetalhamentoCurso detalhe = cursoService.criarCurso(cadastro);

        assertThat(detalhe).isNotNull();
        assertThat(detalhe.nome()).isEqualTo("Java Backend");
        assertThat(detalhe.categoria()).isEqualTo(Categoria.BACKEND);

        // Confirma que salvou no banco
        assertThat(cursoRepository.findById(detalhe.id())).isPresent();
    }

    @Test
    void listarCursos_deveRetornarCursosExistentes() {
        cursoRepository.save(new Curso(null, "Frontend Básico", Categoria.FRONTEND));
        cursoRepository.save(new Curso(null, "DevOps Avançado", Categoria.DEVOPS));

        List<DetalhamentoCurso> cursos = cursoService.listarCursos();

        assertThat(cursos).hasSizeGreaterThanOrEqualTo(2);
        assertThat(cursos).extracting(DetalhamentoCurso::nome)
                .contains("Frontend Básico", "DevOps Avançado");
    }

    @Test
    void buscarPorNome_deveRetornarCursosComNomeContendo() {
        cursoRepository.save(new Curso(null, "Spring Boot", Categoria.BACKEND));
        cursoRepository.save(new Curso(null, "React Avançado", Categoria.FRONTEND));

        List<DetalhamentoCurso> resultados = cursoService.buscarPorNome("spring");

        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).nome()).isEqualTo("Spring Boot");
    }

    @Test
    void deletarCurso_deveRemoverCursoSemTopicos() {
        Curso curso = cursoRepository.save(new Curso(null, "Curso Teste", Categoria.DATA_SCIENCE));

        cursoService.deletarCurso(curso.getId());

        assertThat(cursoRepository.existsById(curso.getId())).isFalse();
    }

}
