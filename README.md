

https://github.com/user-attachments/assets/3dc88b86-d57b-4bc0-ad66-d3c399901aeb

# 📚 LiterAlura


Aplicação de console em **Java 17**, que consome dados da [API Gutendex](https://gutendex.com), registra livros no **PostgreSQL**, e oferece filtros por autor, idioma, ano, estatísticas de popularidade e muito mais.

> 💡 Projeto desenvolvido como parte do **Challenge Back-End da Alura**.

---

## 🎯 Funcionalidades

- 🔎 Buscar livros por título via API Gutendex  
- 💾 Registrar livros no banco de dados evitando duplicatas  
- 📚 Listar livros por autor, idioma, ano, top 10 e autores vivos  
- 📈 Ver estatísticas como total/média de downloads  
- 💬 Interface de menu 100% interativa via terminal  

---

## 🛠️ Requisitos

| Requisito     | Versão recomendada |
|---------------|--------------------|
| Java          | 17+                |
| PostgreSQL    | 14+                |
| Maven         | 3.8+               |
| IDE           | IntelliJ (ou outra)|
| Internet      | Para acessar a API |

---

## 📥 Como clonar o projeto

```bash
git clone https://github.com/AndersonRedfield/ChallengeLiterAlura.git
cd ChallengeLiterAlura

```

---

## 🧩 Dependências (Maven)

Inclua isso no seu `pom.xml`:

```xml
<dependencies>

		<dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId>
			<version>3.43.2.0</version>
		</dependency>


		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.10.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

```

---

## 🗄️ Configuração do Banco de Dados PostgreSQL

Execute os comandos abaixo no terminal do PostgreSQL (`psql`) ou pelo `pgAdmin`:

### 1. Criar banco de dados e usuário

```sql
CREATE DATABASE literalura;
CREATE USER literalura WITH PASSWORD 'sua_senha_segura'; **Substitua a "sua_senha_segura" pela senha cadastrada no seu banco de dados Postgres**
GRANT ALL PRIVILEGES ON DATABASE literalura TO literalura;
```

### 2. Criar a tabela de livros

```sql
CREATE TABLE livros (
  id SERIAL PRIMARY KEY,
  titulo TEXT,
  autor TEXT,
  nascimento_autor INT,
  falecimento_autor INT,
  downloads INT,
  idioma TEXT
);
```

### 3. Conceder permissões

```sql
GRANT ALL PRIVILEGES ON TABLE livros TO literalura;
GRANT USAGE, SELECT ON SEQUENCE livros_id_seq TO literalura;
```

---

## 🧠 Conexão com o banco (LivroRepository.java)

No seu repositório:

```java
private final String URL = "jdbc:postgresql://localhost:5432/literalura";
private final String USUARIO = "literalura";
private final String SENHA = "sua_senha_segura"; **Substitua pela senha cdastrada no seu código**
```

---

## ▶️ Como executar

1. Certifique-se de que o PostgreSQL está rodando localmente na porta padrão (5432).  
2. Na sua IDE, execute a classe:

```
LiteraluraApplication.java
```

3. Você verá o menu abaixo no terminal:

```
=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros em determinado idioma
6 - Ver estatísticas de popularidade
7 - Ver os 10 livros mais populares
8 - Listar idiomas disponíveis
9 - Buscar livros por autor registrado
0 - Sair
```

---

## 🧪 Funcionalidades Detalhadas

| Opção | Ação                                                             |
|-------|------------------------------------------------------------------|
| 1     | Busca livros por título e permite registro no banco             |
| 2     | Lista todos os livros registrados                               |
| 3     | Exibe autores únicos cadastrados                                |
| 4     | Filtra autores vivos a partir de determinado ano                |
| 5     | Lista livros de um idioma específico (`pt`, `en`, `fr`, etc.)   |
| 6     | Exibe estatísticas (média, total, min, max downloads)           |
| 7     | Lista os 10 livros mais populares por número de downloads       |
| 8     | Mostra idiomas encontrados nos livros registrados               |
| 9     | Busca por autor parcial (com ou sem acento)                     |
| 0     | Encerra o programa                                               |

---

## 🧱 Estrutura do Projeto

```
literalura/
├── model/
│   ├── Livro.java
│   └── Autor.java
├── repository/
│   └── LivroRepository.java
├── service/
│   └── GutendexService.java
├── view/
│   └── Menu.java
├── LiteraluraApplication.java
```

---

## 📝 application.properties (caso migre para Spring futuramente)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=literalura **Aqui substitua pelo usuário cadastrado no banco de dado Postgres**
spring.datasource.password=sua_senha_segura **Substitua "sua_senha_segura" pela sua senha cadastrada no banco de dados Postgres**
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

**Substitua o "literalura" e a "sua_senha_segura" pela senha cadastrada no banco de dados Postgres**

```

---

## 📈 Resultado Esperado

- ✅ Conexão 100% com o PostgreSQL  
- ✅ Registro de livros sem duplicatas  
- ✅ Consulta por título, autor, idioma, ano  
- ✅ Interface limpa no terminal com feedback claro  
- ✅ Estatísticas bem apresentadas  

---

## ✍️ Autor

**Anderson Redfield**  
🔗 GitHub: [@AndersonRedfield](https://github.com/AndersonRedfield)


## 🚀 Challenge Alura

Projeto desenvolvido para o **Challenge Back-End da Alura**, com foco em:


- ✅ Consumo de APIs externas (Gutendex)
- ✅ Persistência de dados com JDBC + PostgreSQL
- ✅ Boas práticas de orientação a objetos em Java
- ✅ Interface de terminal funcional e elegante
- ✅ Manipulação de JSON com Gson e filtros com Streams


## 💡 Licença

Este projeto foi desenvolvido como parte do **Desafio Back-End da Alura** e está disponível apenas para fins educacionais.
