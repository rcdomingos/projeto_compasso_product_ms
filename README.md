![Logo Compasso](https://compasso.com.br/wp-content/uploads/2020/07/LogoCompasso-Negativo.png)

# Catálogo de produtos
Reginaldo Cardoso Domingos


## Objetivo
 Implementar um catálogo de produtos com Java e Spring Boot.

### Dependências utilizadas:
- Spring Data JPA
- Spring Web
- Spring DevTools
- Swagger: Documentar a API
- H2: Banco de dados em memória.


## Executando o projeto

Para executar o projeto (com o [Maven](https://maven.apache.org/) instalado) execute o comando no diretório do projeto: 

```
   mvn spring-boot:run
```

Para executar os testes:
```
   mvn test
```
### Endpoints

Os endpontis disponibilizados para API (com as configurações padrão o serviço subira em http://localhost:9999) são: 

| Verbo HTTP  |  Resource path    |           Descrição           |
|-------------|:-----------------:|------------------------------:|
| POST        |  /products        |   Criação de um produto       |
| PUT         |  /products/{id}   |   Atualização de um produto   |
| GET         |  /products/{id}   |   Busca de um produto por ID  |
| GET         |  /products        |   Lista de produtos           |
| GET         |  /products/search |   Lista de produtos filtrados |
| DELETE      |  /products/{id}   |   Deleção de um produto       |

Os detalhes dos recursos e endpontis podem ser obtidos pelo Swagger na url http://localhost:9999/swagger-ui.html

