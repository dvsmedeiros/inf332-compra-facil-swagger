package com.unicamp.inf332.comprafacil.controller.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("INF332 - API Compra Fácil (Grupo 3) - OpenAPI 3.0")
                        .description("""
                                   A API Compra Fácil é uma plataforma que permite a gestão de afiliados e produtos para um programa de afiliação de e-commerce. Através desta API, parceiros externos poderão criar aplicativos web e mobile que se integram ao Compra Fácil, facilitando a revenda de produtos e promoções.
                                                                                                              
                                   A API é baseada em RESTful e utiliza o formato JSON para as requisições e respostas também utiliza Hypermedia (HATEOAS).
                                                              
                                   Links úteis:
                                       \n - [Repositório Compra Fácil](https://github.com/dvsmedeiros/inf332-compra-facil-swagger)
                                                
                                   Grupo 3:                                       
                                       \n - Davisson Medeiros
                                       \n - Drielle de Lima
                                       \n - Lucas Monaro
                                       \n - Lucas Santos
                                       \n - Vanderlei Andrade                                                                                          
                                """)
                        .termsOfService("terms")
                        .contact(new Contact().email("grupo3@inf332.com.br"))
                        .license(new License().name("GNU"))
                        .version("1.0")
                );
    }
}