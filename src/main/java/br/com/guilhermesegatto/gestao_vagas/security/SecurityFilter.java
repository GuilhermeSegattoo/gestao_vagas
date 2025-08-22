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
import br.com.guilhermesegatto.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private JWTProvider jwtProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");
        
        System.out.println("🔍 URI: " + request.getRequestURI());
        System.out.println("🔍 Header: " + header);
        
        if (request.getRequestURI().startsWith("/company") || 
            (request.getRequestURI().startsWith("/job") && !"GET".equals(request.getMethod()))) {
            if (header != null) {
                var companyId = this.jwtProvider.validateToken(header);
                System.out.println("🔍 Token validado: " + (companyId != null ? "VÁLIDO" : "INVÁLIDO"));
                if (companyId != null) {
                    request.setAttribute("company_id", companyId);
                    var roles = java.util.List.of(new SimpleGrantedAuthority("ROLE_COMPANY"));
                    var auth = new UsernamePasswordAuthenticationToken(companyId, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("✅ Autenticação definida para: " + companyId);
                }
            } else {
                System.out.println("❌ Nenhum header Authorization encontrado");
            }
        }
        
        
        filterChain.doFilter(request, response);
    }
    
}
