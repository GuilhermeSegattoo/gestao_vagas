package br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto.CandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    public CandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new RuntimeException("Candidate not found");
            });
            
        return CandidateResponseDTO.builder()
            .id(candidate.getId())
            .name(candidate.getName())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .description(candidate.getDescription())
            .curriculum(candidate.getCurriculum())
            .createdAt(candidate.getCreatedAt())
            .build();
    }
}