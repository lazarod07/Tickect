package com.cristian.ticket.configuration.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Ticket API")
                        .version("1.0")
                        .description("Ticket API"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearer-jwt", new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
