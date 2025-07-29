package br.com.artheus.forumhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Usuários - liberar todas operações CRUD
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").permitAll()

                        // Cursos - liberar todas operações CRUD
                        .requestMatchers(HttpMethod.POST, "/cursos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/cursos/**").permitAll()

                        // Tópicos - liberar todas operações CRUD
                        .requestMatchers(HttpMethod.POST, "/topicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topicos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/topicos/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/topicos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/topicos/**").permitAll()

                        // Qualquer outra requisição requer autenticação
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
