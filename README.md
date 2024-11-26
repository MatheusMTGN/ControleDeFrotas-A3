# 🚚 Sistema de Controle de Frotas de Caminhões

Um sistema de gerenciamento de caminhões, motoristas e rotas de uma frota de caminhões, desenvolvido com **Spring Boot** e **MySQL**. Este projeto permite o gerenciamento de dados essenciais para operações logísticas, com uma arquitetura **MVC**.

## 📝 Sumário
- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- -[Endpoints](#endpoints)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Configuração](#configuração)


## 📖 Sobre o Projeto
O **Sistema de Controle de Frotas de Caminhões** é uma aplicação web projetada para gerenciar frotas. Ele possibilita o controle de veículos - caminhões -, motoristas e rotas, otimizando o fluxo de informações e proporcionando uma visão geral da logística da frota.

O projeto utiliza **Spring Boot** para o backend, com o banco de dados **MySQL** para persistência de dados com o **FlyWay**. O projeto segue o padrão de arquitetura **MVC** (Model-View-Controller), separando de forma clara as camadas de dados, lógica de negócio e visualização.

## 🚀 Funcionalidades
### Veículos
- CRUD completo para gerenciar dados dos veículos;
- Listagem de todos os veículos registrados na frota;
- Listagem de caminhão por placa;
  

### Motoristas
- CRUD completo para o gerenciamento de motoristas;
- Consulta de motoristas ativos;
- Alocação em rotas;
- Alocação de motorista e caminhão;

### Rotas
- CRUD completo;
- Filtragem de rotas de um motorista em específico;

## 🏛️ Arquitetura do Sistema
Este sistema segue a arquitetura MVC:
- **Model**: Representa as classes de domínio (`Veiculo`, `Motorista`, `Rota`) e a persistência no banco de dados.
- **View**: Lógica de negócio e serviço;
- **Controller**: Controladores REST que expõem endpoints para as operações CRUD e lógicas de negócio.

## 📍Endpoints
**🧑 Motorista**
- **POST** `localhost:8080/motoristas` -> para o cadastro de motoristas, com informações necessárias: nome, cpf, dataNascimento, cnh, telefone e email;
- **GET** `localhost:8080/motoristas` -> para listas todos os motoristas;
- **GET** `localhost:8080/motoristas/ativos` -> para listar todos os motoristas com cadastro ativo; 
- **GET** `localhost:8080/motoristas/{id}` -> para filtrar um motorista pelo seu ID;
- **PUT** `localhost:8080/motoristas/{id}` -> para atualizar os dados do motorista. Os únicos campos que permitem alteração são: **e-mail** e **telefone**;
- **DELET** `localhost:8080/motoristas/{id}` -> para "deletar" o motorista pelo seu ID. O campo de deletar apenas faz com que o cadastro do motorista fique desativado, mas não o excluí do banco de dados;
- **GET** `localhost:8080/motoristas/detalhes{id}` -> detalhes totais do motorista, com todos os seus campos e rotas;
- **POST** `localhost:8080/motoristas/{id}/vincular-caminhao/{placa}` -> vincula um motorista à um caminhão;
** 🚛 Caminhão**
- **POST** `localhost:8080/caminhoes`-> para o cadastro do caminhão, com informações necessárias: placa, modelo, ano;
- **GET** `localhost:8080/caminhoes/{placa}` -> para filtrar um caminhão pela sua placa;
- **GET** `localhost:8080/caminhoes` -> para listar todos os caminhões;
- **POST** `localhost:8080/caminhoes{placa}` -> para atualizar dados do caminhão. Os únicos campos que permitem alteração são: ano e modelo;
- **DELETE** `localhost:8080/caminhoes{placa}` -> deleta um caminhão;

** 🗺️ Rotas**
- **POST** `localhost:8080/rotas` -> para o cadastro de rotas, com informações necessárias: origem, destino e motorista (id);
- **GET** `localhost:8080/rotas` -> lista todas as rotas;
- **GET** `localhost:8080/rotas/motorista/{id}` -> lista todas as rotas de um motorista específico;
- **PUT** `localhost:8080/rotas/{id}` -> atualiza uma rota específica;
- **DELETE** `localhost:8080/rotas/{id}` -> deleta uma rota pelo id;
## 💻 Tecnologias Utilizadas
- **Java 17**;
- **Spring Boot** com os módulos:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - **Lombok** para reduzir boilerplate de código;
  - **Flyway** para migrações de banco de dados;
  - **MySQL** para armazenamento dos dados;
  - **Jackson** para Json;
  
## 🛠️ Configuração
  - **JDK 17** instalado
  - **MySQL** rodando localmente ou em um servidor
  - **Maven** para gerenciar as dependências

