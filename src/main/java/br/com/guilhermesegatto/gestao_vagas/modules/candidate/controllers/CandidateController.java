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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

   
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFIlterUseCase listAllJobsByFIlterUseCase;
  
  @PostMapping("/")
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CreateCandidateDTO createCandidateDTO) {
        try{
            var result = this.createCandidateUseCase.execute(createCandidateDTO); 
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }
    
    @GetMapping("/profile")
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
    @Tag(name = "Candidato", description = "Endpoints para candidatos")
    @Operation(summary = "Listar Vagas por Filtro", description = "Lista todas as vagas que contêm o filtro na descrição. Requer autenticação.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
        })
    })
    
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> listJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFIlterUseCase.execute(filter);
    }
}
