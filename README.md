# 🔐 Spring Security – Estudo Prático

Este repositório contém um projeto desenvolvido com o objetivo de **estudar Spring Security na prática**, focando em autenticação e autorização em uma API REST.

O projeto foi originalmente baseado em conteúdos mais antigos e **atualizado para versões modernas**, resolvendo problemas reais de compatibilidade e configuração.

---

## 🚀 Tecnologias Utilizadas

* ☕ **Java 21**
* 🌱 **Spring Boot 3.x**
* 🔐 **Spring Security 6**
* 🗄️ **Spring Data JPA**
* 💾 **H2 Database (em memória)**
* 📦 **Maven**

---

## 🎯 Objetivo do Projeto

* Entender como funciona o **Spring Security** no cenário atual
* Implementar **autenticação baseada em banco de dados**
* Trabalhar com **roles e autorização por endpoint**
* Compreender o papel de:

  * `SecurityFilterChain`
  * `UserDetailsService`
  * `UserDetails`

Este projeto tem foco **educacional**, priorizando clareza e aprendizado prático.

---

## 🔐 Controle de Acesso

### Endpoints

| Endpoint    | Acesso          |
| ----------- | --------------- |
| `/`         | Público         |
| `/users`    | USERS, MANAGERS |
| `/managers` | MANAGERS        |

### Usuários de Teste

| Usuário | Senha     | Role     |
| ------- | --------- | -------- |
| admin   | master123 | MANAGERS |
| user    | user123   | USERS    |

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

* Java 21 instalado
* Maven configurado

### Passos

```bash
# clone o repositório
git clone <URL_DO_REPOSITORIO>

# entre no projeto
cd dio-spring-security

# rode a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## 🧠 Aprendizados Principais

* Diferenças entre Spring Security antigo e atual
* Uso de `SecurityFilterChain` no lugar do `WebSecurityConfigurerAdapter`
* Migração de `javax.persistence` para `jakarta.persistence`
* Importância do **PasswordEncoder**
* Autenticação usando dados persistidos no banco

---

## 🔜 Próximos Passos

* Implementar **JWT (JSON Web Token)**
* Criar autenticação stateless
* Separar melhor camadas de segurança

---

## 📌 Observação

Este projeto foi desenvolvido como parte do processo de aprendizado. Sugestões e melhorias são bem-vindas.

---

👨‍💻 Desenvolvido por **Keven Godinho Pereira**

📚 Estudante de Desenvolvimento Backend | Java | Spring Boot
