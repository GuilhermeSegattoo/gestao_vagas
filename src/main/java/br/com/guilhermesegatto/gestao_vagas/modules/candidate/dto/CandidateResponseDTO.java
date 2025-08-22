package br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;
    private String curriculum;
    private LocalDateTime createdAt;
}