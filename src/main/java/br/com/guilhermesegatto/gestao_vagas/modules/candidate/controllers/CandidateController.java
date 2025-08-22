package br.com.guilhermesegatto.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

   
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;
  
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
}
