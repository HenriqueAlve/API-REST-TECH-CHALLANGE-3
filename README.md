# Sistema de Gestão Hospitalar - API REST

## 📋 Descrição

Sistema de Gestão Hospitalar desenvolvido como parte do Tech Challenge da FIAP - Fase 3. Esta aplicação REST API permite o gerenciamento completo de consultas médicas, pacientes, médicos e enfermeiros em um ambiente hospitalar.

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura **MVC (Model-View-Controller)** adaptado para APIs REST, organizado da seguinte forma:

### Camadas da Aplicação

```
src/main/java/fiap/gestao/hospitalar/
│
├── controller/          # Camada de Controle (Controller)
│   ├── AuthenticationController.java
│   ├── ConsultaController.java
│   ├── EnfermeiroController.java
│   ├── MedicoController.java
│   ├── PacienteController.java
│   └── UserController.java
│
├── service/            # Camada de Serviços (Business Logic)
│   └── [Serviços de negócio]
│
├── repository/         # Camada de Acesso a Dados (Data Access)
│   └── [Repositórios JPA]
│
├── entities/           # Camada de Modelo (Model)
│   ├── Consulta.java
│   ├── Enfermeiro.java
│   ├── Medico.java
│   ├── Paciente.java
│   └── User.java
│
├── dto/               # Data Transfer Objects
│   └── [DTOs de requisição e resposta]
│
├── security/          # Configurações de Segurança
│   ├── SecurityConfig.java
│   ├── SecurityFilter.java
│   └── TokenService.java
│
├── configuration/     # Configurações Gerais
│   └── RabbitMQConfig.java
│
└── exceptions/        # Tratamento de Exceções
    └── [Exception handlers]
```

### Padrão MVC Explicado

- **Model (Modelo)**: Representado pelas entidades JPA (`entities/`) que mapeiam as tabelas do banco de dados
- **View (Visualização)**: Como é uma API REST, a "view" são os DTOs (`dto/`) que definem o formato JSON das respostas
- **Controller (Controlador)**: Os controllers (`controller/`) que recebem as requisições HTTP e orquestram as operações

## 🚀 Tecnologias Utilizadas

### Core Framework
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Maven** - Gerenciador de dependências e build

### Frameworks e Bibliotecas
- **Spring Data JPA** - Persistência de dados e ORM
- **Spring Web** - Desenvolvimento de APIs REST
- **Spring Security** - Autenticação e autorização
- **Spring AMQP** - Integração com RabbitMQ para mensageria

### Banco de Dados
- **PostgreSQL** - Banco de dados relacional
- **Hibernate** - ORM (Object-Relational Mapping)

### Segurança
- **JWT (JSON Web Token)** - Autenticação baseada em tokens
- **Auth0 Java-JWT 4.4.0** - Biblioteca para manipulação de JWT
- **BCrypt** - Hash de senhas

### Mensageria
- **RabbitMQ** - Message broker para comunicação assíncrona

### Utilitários
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validação de dados

## 📦 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

- **Java JDK 17** ou superior
- **Maven 3.6+**
- **PostgreSQL 12+**
- **RabbitMQ 3.8+** (opcional, para funcionalidades de mensageria)
- **Git** (para clonar o repositório)

## ⚙️ Configuração e Instalação

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd API-REST-TECH-CHALLANGE-3
```

### 2. Configurar o Banco de Dados PostgreSQL

#### 2.1. Criar o Banco de Dados

Acesse o PostgreSQL e execute:

```sql
CREATE DATABASE hospitalar;
```

#### 2.2. Configurar Credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configuração do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/hospitalar
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver
```

**Importante**: Ajuste `username` e `password` conforme suas credenciais do PostgreSQL.

### 3. Configurar RabbitMQ (Opcional)

Se você for utilizar as funcionalidades de mensageria, configure o RabbitMQ no arquivo `src/main/resources/application.yml`:

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 4. Configurar JWT Secret

Para segurança, configure uma chave secreta para geração de tokens JWT:

**Opção 1**: Variável de ambiente (recomendado)
```bash
# Windows (CMD)
set JWT_SECRET=sua-chave-secreta-super-segura

# Windows (PowerShell)
$env:JWT_SECRET="sua-chave-secreta-super-segura"

# Linux/Mac
export JWT_SECRET=sua-chave-secreta-super-segura
```

**Opção 2**: Editar diretamente no `application.properties`
```properties
api.security.token.secret=sua-chave-secreta-super-segura
```

### 5. Compilar o Projeto

```bash
mvn clean install
```

Ou, para pular os testes:

```bash
mvn clean install -DskipTests
```

### 6. Executar a Aplicação

**Opção 1**: Via Maven
```bash
mvn spring-boot:run
```

**Opção 2**: Via JAR compilado
```bash
java -jar target/rest.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 🔐 Autenticação

A API utiliza autenticação JWT (JSON Web Token). Para acessar endpoints protegidos:

### 1. Registrar um Usuário

```http
POST /auth/register
Content-Type: application/json

{
  "login": "usuario@email.com",
  "password": "senha123",
  "role": "ADMIN"
}
```

### 2. Fazer Login

```http
POST /auth/login
Content-Type: application/json

{
  "login": "usuario@email.com",
  "password": "senha123"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Usar o Token

Inclua o token no header de todas as requisições protegidas:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📍 Principais Endpoints

### Autenticação
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Autenticar usuário

### Pacientes
- `GET /pacientes` - Listar todos os pacientes
- `GET /pacientes/{id}` - Buscar paciente por ID
- `POST /pacientes` - Cadastrar novo paciente
- `PUT /pacientes/{id}` - Atualizar paciente
- `DELETE /pacientes/{id}` - Remover paciente

### Médicos
- `GET /medicos` - Listar todos os médicos
- `GET /medicos/{id}` - Buscar médico por ID
- `POST /medicos` - Cadastrar novo médico
- `PUT /medicos/{id}` - Atualizar médico
- `DELETE /medicos/{id}` - Remover médico

### Enfermeiros
- `GET /enfermeiros` - Listar todos os enfermeiros
- `GET /enfermeiros/{id}` - Buscar enfermeiro por ID
- `POST /enfermeiros` - Cadastrar novo enfermeiro
- `PUT /enfermeiros/{id}` - Atualizar enfermeiro
- `DELETE /enfermeiros/{id}` - Remover enfermeiro

### Consultas
- `GET /consultas` - Listar todas as consultas
- `GET /consultas/{id}` - Buscar consulta por ID
- `POST /consultas` - Agendar nova consulta
- `PUT /consultas/{id}` - Atualizar consulta
- `DELETE /consultas/{id}` - Cancelar consulta

## 🗄️ Modelo de Dados

### Entidades Principais

- **User**: Usuários do sistema (autenticação)
- **Paciente**: Pacientes cadastrados
- **Medico**: Médicos do hospital
- **Enfermeiro**: Enfermeiros do hospital
- **Consulta**: Consultas médicas agendadas

## 🔧 Configurações Avançadas

### Alterar Porta do Servidor

Edite `application.properties`:

```properties
server.port=8081
```

### Configurar Logs

```properties
logging.level.org.springframework.security=DEBUG
logging.level.fiap.gestao.hospitalar=DEBUG
```

### Configurar JPA/Hibernate

```properties
# Estratégia de criação do schema
spring.jpa.hibernate.ddl-auto=update

# Mostrar SQL no console
spring.jpa.show-sql=true

# Formatar SQL
spring.jpa.properties.hibernate.format_sql=true
```

Opções para `ddl-auto`:
- `none`: Não faz nada
- `validate`: Valida o schema
- `update`: Atualiza o schema (recomendado para desenvolvimento)
- `create`: Cria o schema (remove dados existentes)
- `create-drop`: Cria e remove ao finalizar

## 🧪 Testes

Executar todos os testes:

```bash
mvn test
```

Executar com relatório de cobertura:

```bash
mvn test jacoco:report
```

## 📦 Build para Produção

### Gerar JAR

```bash
mvn clean package -DskipTests
```

O arquivo JAR será gerado em `target/rest.jar`

### Executar em Produção

```bash
java -jar target/rest.jar --spring.profiles.active=prod
```

## 🐳 Docker (Opcional)

Se disponível, você pode executar a aplicação usando Docker:

```bash
docker build -t gestao-hospitalar .
docker run -p 8080:8080 gestao-hospitalar
```

## 🛠️ Troubleshooting

### Erro: "Database does not exist"

Certifique-se de criar o banco de dados `hospitalar` no PostgreSQL:

```sql
CREATE DATABASE hospitalar;
```

### Erro: "Could not connect to database"

Verifique se:
- O PostgreSQL está rodando
- As credenciais em `application.properties` estão corretas
- A URL de conexão está correta

### Erro: "Port 8080 already in use"

Altere a porta no `application.properties`:

```properties
server.port=8081
```

### Erro de autenticação RabbitMQ

Se não estiver usando RabbitMQ, você pode desabilitar a configuração comentando as dependências relacionadas no `pom.xml`.

## 📝 Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `JWT_SECRET` | Chave secreta para JWT | `batman batman batman` |
| `SPRING_DATASOURCE_URL` | URL do banco de dados | `jdbc:postgresql://localhost:5432/hospitalar` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `root` |

## 👥 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

---

**Desenvolvido com ❤️ usando Spring Boot**
