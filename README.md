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

---

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

### Login
**Endpoint:**
```
POST /auth/login
Content-Type: application/json
```

**Exemplo de corpo da requisição:**
```json
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

### 🔐 1. Configuração das variáveis de ambiente

Configure as variáveis diretamente no IntelliJ:

1. Vá em **Run > Edit Configurations...**  
2. Selecione a configuração da sua aplicação (`ForumHubApplication`)  
3. No campo **Environment variables**, adicione:

```
DB_URL=jdbc:mysql://localhost:3306/forumhub;DB_USERNAME=seu_usuario;DB_PASSWORD=sua_senha;JWT_SECRET_KEY=sua_chave_secreta_segura
```

> Use `;` como separador se estiver no Windows, ou `:` no macOS/Linux.

### 📄 2. application.properties

No seu `src/main/resources/application.properties`, use:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET_KEY}
```

---

## ✅ Etapas para rodar

### 1. Clone o repositório:

```bash
git clone https://github.com/arthsdev/forumhub.git
cd forumhub
```

### 2. Configure o banco de dados MySQL

Crie um banco chamado `forumhub`.

### 3. Rode o projeto

```bash
./mvnw spring-boot:run
```

Ou execute a classe `ForumHubApplication` pela sua IDE.

### 4. Acesse a API

Abra no navegador:  
🔗 [http://localhost:8080](http://localhost:8080)

---

## 📦 Migrations com Flyway

As tabelas são criadas automaticamente via scripts Flyway, localizados em:

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
