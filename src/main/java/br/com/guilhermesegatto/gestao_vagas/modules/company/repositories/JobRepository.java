package br.com.guilhermesegatto.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    
    // contains - LIKE = percorre o campo procurando o valor

    // select * from job_entity where description like '%filter%'
    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);
    
}
