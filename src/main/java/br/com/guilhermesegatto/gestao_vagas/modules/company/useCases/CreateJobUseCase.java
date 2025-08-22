package br.com.guilhermesegatto.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;
    
    public JobEntity execute(CreateJobDTO createJobDTO, UUID companyId) {
        var jobEntity = new JobEntity();
        jobEntity.setDescription(createJobDTO.getDescription());
        jobEntity.setLevel(createJobDTO.getLevel());
        jobEntity.setBenefits(createJobDTO.getBenefits());
        jobEntity.setCompanyId(companyId);
        
        return this.jobRepository.save(jobEntity);
    }  
}
