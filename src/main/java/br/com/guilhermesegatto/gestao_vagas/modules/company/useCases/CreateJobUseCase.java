package br.com.guilhermesegatto.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;
    
    @Transactional
    public JobEntity execute(CreateJobDTO createJobDTO, UUID companyId) {
        System.out.println("🔍 Criando job para company: " + companyId);
        System.out.println("🔍 Dados do job: " + createJobDTO.getDescription() + " - " + createJobDTO.getLevel());
        
        var jobEntity = new JobEntity();
        jobEntity.setDescription(createJobDTO.getDescription());
        jobEntity.setLevel(createJobDTO.getLevel());
        jobEntity.setBenefits(createJobDTO.getBenefits());
        jobEntity.setCompanyId(companyId);
        
        var savedJob = this.jobRepository.save(jobEntity);
        System.out.println("✅ Job salvo com ID: " + savedJob.getId());
        
        return savedJob;
    }  
}
