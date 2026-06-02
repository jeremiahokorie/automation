package com.automation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server prodServer = new Server();
        prodServer.setUrl("https://eprocess.space");
        prodServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setName("Process Automation Team");
        contact.setEmail("tech@process.ng");

        Info info = new Info()
                .title("Process Automation Service API")
                .version("1.0.0")
                .description("Microservice for Process Automation")
                .contact(contact)
                .license(new License().name("Proprietary").url("https://eprocess.space"));

        return new OpenAPI()
                .info(info)
                .servers(List.of(prodServer));
    }
}
