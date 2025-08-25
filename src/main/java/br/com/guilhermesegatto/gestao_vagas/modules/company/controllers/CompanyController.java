package br.com.guilhermesegatto.gestao_vagas.modules.company.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.ComppanyEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Operações relacionadas às empresas")
public class CompanyController {
 
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    @Operation(
        summary = "Cadastrar nova empresa", 
        description = "Cria uma nova empresa no sistema com os dados fornecidos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso",
            content = @Content(schema = @Schema(implementation = ComppanyEntity.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou empresa já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCompanyDTO createCompanyDTO) {
      try {
            var result = this.createCompanyUseCase.execute(createCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}
