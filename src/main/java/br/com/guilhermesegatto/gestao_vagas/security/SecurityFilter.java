package br.com.guilhermesegatto.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.CompanyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Value("${security.token.secret}")
    private String secretKey;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");
        
        System.out.println("🔍 URI: " + request.getRequestURI());
        System.out.println("🔍 Header: " + header);
        
        if (request.getRequestURI().startsWith("/company") || request.getRequestURI().startsWith("/job")) {
            if (header != null) {
                var token = this.validateToken(header);
                System.out.println("🔍 Token validado: " + (token != null ? "VÁLIDO" : "INVÁLIDO"));
                if (token != null) {
                    request.setAttribute("company_id", token);
                    var roles = java.util.List.of(new SimpleGrantedAuthority("ROLE_COMPANY"));
                    var auth = new UsernamePasswordAuthenticationToken(token, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("✅ Autenticação definida para: " + token);
                }
            } else {
                System.out.println("❌ Nenhum header Authorization encontrado");
            }
        }
        
        if (request.getRequestURI().startsWith("/candidate")) {
            System.out.println("🔍 CANDIDATE - URI: " + request.getRequestURI());
            if (header != null) {
                System.out.println("🔍 CANDIDATE - Header encontrado: " + header.substring(0, Math.min(20, header.length())) + "...");
                var token = this.validateToken(header);
                System.out.println("🔍 CANDIDATE - Token validado: " + (token != null ? "VÁLIDO" : "INVÁLIDO"));
                if (token != null) {
                    request.setAttribute("candidate_id", token);
                    var roles = java.util.List.of(new SimpleGrantedAuthority("ROLE_CANDIDATE"));
                    var auth = new UsernamePasswordAuthenticationToken(token, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("✅ CANDIDATE - Autenticação definida para: " + token);
                }
            } else {
                System.out.println("❌ CANDIDATE - Nenhum header Authorization encontrado");
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String validateToken(String token) {
        token = token.replace("Bearer ", "");
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        try {
            var tokenDecoded = JWT.require(algorithm)
                .withIssuer("gestao_vagas")
                .build()
                .verify(token);
                
            return tokenDecoded.getSubject();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}
