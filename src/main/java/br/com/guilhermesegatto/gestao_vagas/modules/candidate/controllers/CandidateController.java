package br.com.guilhermesegatto.gestao_vagas.modules.candidate.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases.ListAllJobsByFIlterUseCase;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações relacionadas aos candidatos")
public class CandidateController {

   
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFIlterUseCase listAllJobsByFIlterUseCase;
  
  @PostMapping("/")
    @Operation(
        summary = "Cadastrar novo candidato", 
        description = "Cria um novo candidato no sistema com os dados fornecidos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Candidato criado com sucesso",
            content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já existe")
    })
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CreateCandidateDTO createCandidateDTO) {
        try{
            var result = this.createCandidateUseCase.execute(createCandidateDTO); 
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }
    
    @GetMapping("/")
    @Operation(
        summary = "Obter perfil do candidato", 
        description = "Retorna as informações do perfil do candidato autenticado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil obtido com sucesso",
            content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
        @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        try {
            var candidateId = request.getAttribute("candidate_id");
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Listar vagas disponíveis", 
        description = "Lista todas as vagas que contêm o filtro na descrição (busca case-insensitive). Requer autenticação de candidato."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de vagas obtida com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - role CANDIDATE necessária")
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> listJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFIlterUseCase.execute(filter);
    }
}
