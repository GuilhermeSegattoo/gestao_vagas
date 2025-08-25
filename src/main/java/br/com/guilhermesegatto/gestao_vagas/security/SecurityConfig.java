package br.com.guilhermesegatto.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;
    
    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    private static final String[] SWAGGER_WHITELIST = {
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> { 
            auth.requestMatchers("/candidate/").permitAll()
                .requestMatchers("/candidate/auth").permitAll()
                .requestMatchers("/company/").permitAll()
                .requestMatchers("/company/auth").permitAll()
                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                .requestMatchers("/swagger-ui/index.html").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/job/").permitAll();
            auth.anyRequest().authenticated();    
            })
            .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
