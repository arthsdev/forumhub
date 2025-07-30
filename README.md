# 🧵 ForumHub API

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-005C84?logo=mysql&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)


Uma API REST para um fórum de perguntas e respostas, inspirada no modelo do Alura Fórum. Desenvolvida com Spring Boot e boas práticas modernas de segurança, autenticação, DTOs e arquitetura em camadas.

---

## 🚀 Funcionalidades

- Autenticação com JWT
- Cadastro e login de usuários
- CRUD de tópicos
- Associação com cursos
- Validações com Bean Validation
- Documentação com Swagger UI
- Migrations com Flyway
- Hash de senha com BCrypt
- Paginação, ordenação e filtros

---

## 🧰 Tecnologias utilizadas

- Java 17  
- Spring Boot 3  
- Spring Security  
- JWT (Json Web Token)  
- MySQL  
- Flyway  
- Maven  
- Swagger UI  
- Jakarta Bean Validation  
- BCrypt  


## 📂 Estrutura do projeto

```
br.com.artheus.forumhub
├── controller      # Endpoints da API (REST Controllers)
├── domain          # Entidades (JPA)
├── dto             # Data Transfer Objects
├── repository      # Interfaces para acesso ao banco
├── service         # Regras de negócio
├── security        # Segurança, JWT e autenticação
```

---

## 🔐 Autenticação

O login é feito via:

```
POST /auth/login
Content-Type: application/json

{
  "login": "usuario",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use o token nas próximas requisições:
```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📖 Documentação da API

Acesse a documentação interativa via Swagger:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🛠️ Rodando o projeto localmente

### 1. Clone o repositório:

```bash
git clone https://github.com/arthsdev/forumhub.git
cd forumhub
```

### 2. Configure o banco de dados MySQL

Crie um banco chamado `forumhub` e edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Rode o projeto

Via terminal:

```bash
./mvnw spring-boot:run
```

Ou rode a classe `ForumHubApplication` na sua IDE.

### 4. Acesse a API

Abra o navegador:

```
http://localhost:8080
```

---

## 📦 Migrations com Flyway

As tabelas são criadas automaticamente via scripts Flyway localizados em:

```
src/main/resources/db/migration
```

---

## ✅ Status do projeto

🚧 Em desenvolvimento com foco em boas práticas de arquitetura, segurança e aprendizado com Spring Boot.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido por [Artheus Dev](https://github.com/arthsdev)
