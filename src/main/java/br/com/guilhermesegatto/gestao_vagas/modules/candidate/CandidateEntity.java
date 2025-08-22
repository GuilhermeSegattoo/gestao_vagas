package br.com.guilhermesegatto.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "O username deve conter apenas letras, números e os caracteres . _ -")
    private String username;
    @Email(message = "Email deve ser válido")
    private String email;
    
    @Column(length = 100)
    private String password;
    
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;


    
}
