# 🚚 Sistema de Controle de Frotas de Caminhões

Um sistema completo para gerenciar veículos, motoristas e rotas de uma frota de caminhões, desenvolvido com **Spring Boot** e **MySQL**. Este projeto permite o gerenciamento eficiente e seguro de dados essenciais para operações logísticas, com uma arquitetura **MVC**.

## 📝 Sumário
- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Configuração](#configuração)


## 📖 Sobre o Projeto
O **Sistema de Controle de Frotas de Caminhões** é uma aplicação web projetada para gerenciar frotas de forma centralizada. Ele possibilita o controle de veículos, motoristas e rotas, otimizando o fluxo de informações e proporcionando uma visão geral da logística da frota.

O projeto utiliza **Spring Boot** para o backend, com o banco de dados **MySQL** para persistência de dados. O projeto segue o padrão de arquitetura **MVC** (Model-View-Controller), separando de forma clara as camadas de dados, lógica de negócio e visualização.

## 🚀 Funcionalidades
### Veículos
- CRUD completo para gerenciar dados dos veículos;
- Listagem de todos os veículos registrados na frota;

### Motoristas
- CRUD completo para o gerenciamento de motoristas;
- Consulta de motoristas ativos e alocação em rotas;

### Rotas
- Registro de rotas que associam motoristas e veículos;
- Visualização das rotas planejadas e executadas;

## 🏛️ Arquitetura do Sistema
Este sistema segue a arquitetura MVC:
- **Model**: Representa as classes de domínio (`Veiculo`, `Motorista`, `Rota`) e a persistência no banco de dados.
- **View**:
- **Controller**: Controladores REST que expõem endpoints para as operações CRUD e lógicas de negócio.

## 💻 Tecnologias Utilizadas
- **Java 17**;
- **Spring Boot** com os módulos:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - **Lombok** para reduzir boilerplate de código;
  - **Flyway** para migrações de banco de dados;
  - **MySQL** para armazenamento dos dados;
  
## 🛠️ Configuração
  - **JDK 17** instalado
  - **MySQL** rodando localmente ou em um servidor
  - **Maven** para gerenciar as dependências

