package br.com.guilhermesegatto.gestao_vagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(
	  info = @Info(
		  title = "Gestão de Vagas API",
		  version = "v1",
		  description = "API para gerenciar vagas de emprego, incluindo criação, atualização, exclusão e listagem de vagas."
	  )
)
@SecurityScheme(
	name = "jwt_auth",
	description = "JWT Authentication",
	scheme = "bearer",
	type = SecuritySchemeType.HTTP,
	in = SecuritySchemeIn.HEADER,
	bearerFormat = "JWT"
)
public class GestaoVagasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVagasApplication.class, args);
	}

}
