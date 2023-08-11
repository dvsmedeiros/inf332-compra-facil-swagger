## inf332-compra-facil-swagger

### Referências: 
- [Open API 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
- [HATEOAS](https://www.baeldung.com/spring-hateoas-tutorial)

### Requisitos:
- Java 17
- Maven

### Build e execução: 
```bash
$ git clone git@github.com:dvsmedeiros/inf332-compra-facil-swagger.git
$ cd inf332-compra-facil-swagger
$ mvn clean install 
$ java -jar target/compra-facil-0.0.1-SNAPSHOT.jar 
```

### Acesso a documentação

- Swagger: http://localhost:8080/swagger-ui/index.html#/
- json: http://localhost:8080/v3/api-docs
- yaml: http://localhost:8080/v3/api-docs.yaml