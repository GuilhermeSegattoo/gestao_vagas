package br.com.guilhermesegatto.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
    
    @Schema(example = "Desenvolvedor Java para Junior", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Descrição é obrigatória")
    private String description;
    
    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "Nível é obrigatório") 
    private String level;
    
    @Schema(example = "CLT, GymPass, Plano de saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
}