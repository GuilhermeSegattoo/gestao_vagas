package br.com.guilhermesegatto.gestao_vagas.modules.company.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCompanyDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "Username é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "O username deve conter apenas letras, números e os caracteres . _ -")
    private String username;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Length(min = 6, max = 100, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    private String website;
    private String description;
}