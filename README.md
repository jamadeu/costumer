<h1 align="center">ECOMMERCE</h1>

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/jamadeu/costumer/Costumer%20CI)

## Sobre

Projeto desenvolvido com [java](https://docs.oracle.com/en/java/javase/11/docs/api/index.html) + [spring](https://spring.io/).

## Objetivo
Cadastrar os dados pessoais de uma pessoa.

Nome\
E-mail\
CPF\
Data de nascimento\
Todos os dados são obrigatórios.\
Email e cpf devem ser únicos.

## Executando o projeto com Docker

* Requisito: [Docker](https://docs.docker.com/get-docker/)

Execute o docker, abra o terminal em '.../costumer' e execute o comando:

```sh
docker-compose up
```

## Executando o projeto localmente

* Requisito: [Maven](https://maven.apache.org/download.cgi)

Abra o terminal em '.../costumer' e execute:

```sh
./mvnw clean install
```

Após terminal a instalação, execute:

```sh
./mvnw spring-boot:run
```

## Documentação

Para documentação deste projeto foi utilizado o framework Swagger.

Com os serviços em execução, a documentação das API estará disponível em:

http://localhost:8080/swagger-ui.html
