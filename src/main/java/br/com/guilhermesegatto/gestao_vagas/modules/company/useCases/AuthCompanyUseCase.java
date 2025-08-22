package br.com.guilhermesegatto.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    
    @Value("${security.token.secret}")
    private String secretKey;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public String execute(AuthCompanyDTO authCompanyDTO) throws Exception {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername());
        
        if (company == null) {
            throw new Exception("Username/password incorrect");
        }
        
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        
        if (!passwordMatches) {
            throw new Exception("Username/password incorrect");
        }
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create()
            .withIssuer("gestao_vagas")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(company.getId().toString())
            .sign(algorithm);
            
        return token;
    }
}
