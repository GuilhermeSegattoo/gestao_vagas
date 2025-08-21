package br.com.guilhermesegatto.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "company")
@Data
public class ComppanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID ) 
    private UUID id;
    private String name;
    
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "O username deve conter apenas letras, números e os caracteres . _ -")
    private String username;
    @Email(message = "Email deve ser válido")
    private String email;
    @Length(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    private String website;
    private String description;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
