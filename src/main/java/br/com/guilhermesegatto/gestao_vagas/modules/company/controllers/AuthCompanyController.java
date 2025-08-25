package br.com.guilhermesegatto.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa - Autenticação", description = "Autenticação de empresas")
public class AuthCompanyController {
    
    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;
    
    @PostMapping("/auth")
    @Operation(
        summary = "Login de empresa", 
        description = "Autentica uma empresa e retorna o token JWT para acesso às rotas protegidas"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(example = "{\"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"}"))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var token = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}