package br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilhermesegatto.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto.CreateCandidateDTO;

@Service
public class CreateCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public CandidateEntity execute(CreateCandidateDTO createCandidateDTO) {
        // Verifica se existe usuário com mesmo username ou email
        this.candidateRepository
            .findByUsernameOrEmail(createCandidateDTO.getUsername(), createCandidateDTO.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        var candidateEntity = new CandidateEntity();
        candidateEntity.setName(createCandidateDTO.getName());
        candidateEntity.setUsername(createCandidateDTO.getUsername());
        candidateEntity.setEmail(createCandidateDTO.getEmail());
        candidateEntity.setDescription(createCandidateDTO.getDescription());
        candidateEntity.setCurriculum(createCandidateDTO.getCurriculum());
        
        var password = passwordEncoder.encode(createCandidateDTO.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
