package br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.guilhermesegatto.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;
    public ResponseEntity<CandidateEntity> execute(CandidateEntity candidateEntity) {
        // Verifica se existe usuário com mesmo username ou email
        var candidate = this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail());
        
        if (candidate.isPresent()) {
            throw new UserFoundException();
        }

        var createdCandidate = this.candidateRepository.save(candidateEntity);
        return ResponseEntity.ok().body(createdCandidate);
    }
}
