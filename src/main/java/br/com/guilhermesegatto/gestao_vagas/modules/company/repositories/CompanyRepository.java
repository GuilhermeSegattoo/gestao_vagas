package br.com.guilhermesegatto.gestao_vagas.modules.company.repositories;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.ComppanyEntity;

public interface CompanyRepository extends JpaRepository<ComppanyEntity, UUID> {
    
    Optional<ComppanyEntity> findByUsernameOrEmail(String username, String email);
    
}
