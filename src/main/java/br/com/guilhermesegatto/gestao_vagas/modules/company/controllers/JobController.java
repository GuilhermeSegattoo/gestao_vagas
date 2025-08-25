package br.com.guilhermesegatto.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.guilhermesegatto.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Vagas", description = "Operações relacionadas às vagas de emprego")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;
    
    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
        summary = "Cadastrar nova vaga", 
        description = "Cria uma nova vaga de emprego para a empresa autenticada"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vaga criada com sucesso",
            content = @Content(schema = @Schema(implementation = JobEntity.class))),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - role COMPANY necessária")
    })
    @SecurityRequirement(name = "jwt_auth")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        return this.createJobUseCase.execute(createJobDTO, UUID.fromString(companyId.toString()));
    }
    
    @GetMapping("/")
    @Operation(
        summary = "Listar todas as vagas", 
        description = "Retorna todas as vagas disponíveis no sistema (acesso público)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de vagas obtida com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))))
    })
    public java.util.List<JobEntity> list() {
        return this.jobRepository.findAll();
    }
}
