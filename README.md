
# 🧵 ForumHub API

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.x-brightgreen)
![License](https://img.shields.io/github/license/arthsdev/forumhub)
![Tests](https://img.shields.io/badge/testes-unitários%20%7C%20integração%20%7C%20E2E-success)

ForumHub é uma API REST para um fórum de perguntas e respostas, inspirada no modelo da Alura. Foi construída com foco em boas práticas, segurança com JWT e cobertura de testes. Ideal para aprendizado e também para demonstrar habilidades em backend Java.

---

## 🚀 Tecnologias utilizadas

- Java 17  
- Spring Boot 3  
- Spring Security + JWT  
- Spring Data JPA  
- Flyway  
- MySQL / H2  
- JUnit 5, Mockito, Testcontainers  
- Swagger / OpenAPI

---

## 📚 Funcionalidades

- ✅ Cadastro e autenticação de usuários com JWT
- ✅ Criação, listagem, detalhamento e remoção de tópicos
- ✅ Associação de tópicos a cursos e categorias
- ✅ Busca de cursos por nome
- ✅ Endpoints protegidos
- ✅ Validações com Bean Validation
- ✅ Paginação e ordenação
- ✅ Testes unitários, integração e ponta-a-ponta (E2E)

---

## 🛠️ Como rodar localmente

1. Clone o projeto:
   ```bash
   git clone https://github.com/arthsdev/forumhub.git
   cd forumhub
   ```

2. Configure as variáveis de ambiente:
   - `DB_URL=jdbc:mysql://localhost:3306/forumhub`
   - `DB_USERNAME=root`
   - `DB_PASSWORD=sua_senha`
   - `JWT_SECRET_KEY=uma_chave_secreta_segura`

   Você pode configurar isso nas Run Configurations da sua IDE.

3. Rode o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a documentação Swagger:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## 🔐 Autenticação

Faça login no endpoint `/auth/login` com um usuário existente para obter o token JWT. Use o token no header `Authorization: Bearer SEU_TOKEN` para acessar os endpoints protegidos.

---

## 🧪 Como rodar os testes

O projeto possui testes de:

- ✅ Unidade (`@Mock`, `@InjectMocks`)
- ✅ Integração (`@SpringBootTest`)
- ✅ Ponta a ponta (E2E) com autenticação real

Para executar todos os testes via Maven:

```bash
./mvnw test
```

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para detalhes.

---

## 🤝 Contato

Desenvolvido por Fabiano. Para dúvidas ou sugestões, abra uma issue no GitHub ou entre em contato.

---

Obrigado por visitar o ForumHub!
