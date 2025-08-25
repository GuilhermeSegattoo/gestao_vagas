package br.com.guilhermesegatto.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestao de Vagas API")
                        .version("v1")
                        .description("API para gerenciar vagas de emprego, incluindo criacao, atualizacao, exclusao e listagem de vagas."))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("jwt_auth", new SecurityScheme()
                                .name("jwt_auth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("JWT Authentication")));
    }
}