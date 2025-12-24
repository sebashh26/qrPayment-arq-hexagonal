package com.mitocode.qrpayment.infraestructure.in.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QR Payment System API")
                        .version("1.0.0")
                        .description("Hexagonal Architecture QR Payment System - API for managing QR code payments, merchants, and wallets")
                        .contact(new Contact()
                                .name("Mitocode QR Payment Team")
                                .email("support@mitocode.com")
                                .url("https://mitocode.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.mitocode.com")
                                .description("Production Server")));
    }
}
