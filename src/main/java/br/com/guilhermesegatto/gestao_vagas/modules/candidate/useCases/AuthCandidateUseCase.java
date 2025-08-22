package br.com.guilhermesegatto.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.guilhermesegatto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.guilhermesegatto.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;

@Service
public class AuthCandidateUseCase {
    
    @Value("${security.token.secret}")
    private String secretKey;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public String execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws Exception {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username());
        
        if (candidate.isEmpty()) {
            throw new Exception("Username/password incorrect");
        }
        
        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.get().getPassword());
        
        if (!passwordMatches) {
            throw new Exception("Username/password incorrect");
        }
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create()
            .withIssuer("gestao_vagas")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(candidate.get().getId().toString())
            .withClaim("roles", java.util.List.of("CANDIDATE"))
            .sign(algorithm);
            
        return token;
    }
}
