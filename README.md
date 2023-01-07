# People Api

## Descrição

- Projeto de uma API de pessoas, com cadastro de pessoas e seus endereços.

## Tecnologias

* [JAVA 19](https://www.java.com/pt-BR/) - Linguagem de programação (JDK 19).
* [Spring](https://spring.io/projects/spring-boot) - Framework MVC.
* [Apache Maven 3.8.6](https://maven.apache.org/) - Gerenciador de dependências.
* [IntelliJ](https://www.jetbrains.com/idea/) - IDE para desenvolvimento.
* [Docker](https://www.docker.com/) - Serviço de virtualização.

## Como executar a aplicação

- Você pode executar a aplicação da maneira que quiser e utilizando a IDE de sua preferência.
- Caso queira executar a aplicação via linha de comando, execute primeiramente o comando:

```
./mvnw clean package  para linux.

.\mvnw clean package  para windows.
```

- Após isso execute o comando:

```
java -jar <...caminhoParaSeuJar>
```

- Para executar os testes unitários, execute o comando:

```
./mvnw clean test  para linux.

.\mvnw clean test  para windows.
```

- Para executar a aplicação via docker, execute o comando:

```
docker-compose up 
```

## Requisitos de sistema

- Possuir a JDK 11
- Uma IDE ou editor de sua preferência

## Dependências

&emsp;As dependências são declaradas no
arquivo [pom.xml](https://github.com/andersonhsporto/api-consulta-banco/blob/master/pom.xml).

|         Dependência          |                             Descrição                             | Versão  |
|:----------------------------:|:-----------------------------------------------------------------:|:-------:|
|          H2database          | Banco de dados relacional escrito em Java que funciona em memória | 2.1.214 |
| Spring-boot-starter-data-jpa | Responsável por conectar a aplicação Spring como o banco de dados |  2.7.4  |
|   Spring-boot-starter-web    |               Responsável pela camada MVC do Spring               |  2.7.4  |
|        Junit-jupiter         |                  Framework para testes unitários                  |  5.9.1  |
|     SpringDoc-openapi-ui     |             Responsável pela documentação da API Rest             |  1.6.2  |
|     hibernate-validator      |      Responsável pela validação dos dados recebidos pela API      |  6.2.0  |

<p align=left> <b>Minhas informações de contato 📬</b></p>
<p align=left>
<a href="https://github.com/andersonhsporto" target="_blank"><img src="https://img.shields.io/badge/Github-181717?logo=Github&logoColor=white"/></a>  
<a href="mailto:anderson.higo2@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Gmail-EA4335?logo=Gmail&logoColor=white"/></a>
<a href= "https://www.linkedin.com/in/andersonhsporto/"target="_blank"><img src="https://img.shields.io/badge/linkedin-%230077B5.svg?logo=linkedin&logoColor=white"/></a>
</p>
