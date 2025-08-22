package br.com.guilhermesegatto.gestao_vagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
    
    @NotBlank(message = "Descrição é obrigatória")
    private String description;
    
    @NotBlank(message = "Nível é obrigatório") 
    private String level;
    
    private String benefits;
}