package br.com.guilhermesegatto.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.guilhermesegatto.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Zera a autenticação
        SecurityContextHolder.getContext().setAuthentication(null);
        
        String header = request.getHeader("Authorization");
        
        // Verifica se a requisição é para rota de candidato que precisa autenticação
        if (request.getRequestURI().startsWith("/candidate")) {
            System.out.println("🔍 CANDIDATE FILTER - URI: " + request.getRequestURI());
            
            if (header != null) {
                System.out.println("🔍 CANDIDATE FILTER - Header encontrado: " + header.substring(0, Math.min(20, header.length())) + "...");
                
                var candidateId = this.jwtCandidateProvider.validateToken(header);
                System.out.println("🔍 CANDIDATE FILTER - Token validado: " + (candidateId != null ? "VÁLIDO" : "INVÁLIDO"));
                
                if (candidateId != null) {
                    var roles = java.util.List.of(new SimpleGrantedAuthority("ROLE_CANDIDATE"));
                    var auth = new UsernamePasswordAuthenticationToken(candidateId, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    
                    // Adiciona o candidate_id no request para uso posterior
                    request.setAttribute("candidate_id", candidateId);
                    
                    System.out.println("✅ CANDIDATE FILTER - Autenticação definida para candidate: " + candidateId);
                }
            } else {
                System.out.println("❌ CANDIDATE FILTER - Nenhum header Authorization encontrado");
            }
        }
        
        filterChain.doFilter(request, response);
    }
}