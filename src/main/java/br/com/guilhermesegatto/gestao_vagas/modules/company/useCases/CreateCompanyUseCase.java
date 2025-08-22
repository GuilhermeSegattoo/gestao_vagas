package br.com.guilhermesegatto.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.guilhermesegatto.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.ComppanyEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.CompanyRepository;


@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public ComppanyEntity execute(CreateCompanyDTO createCompanyDTO) {
       
        this.companyRepository.findByUsernameOrEmail(createCompanyDTO.getUsername(), createCompanyDTO.getEmail())
            .ifPresent((user) -> {
               throw new UserFoundException();
            });

        var companyEntity = new ComppanyEntity();
        companyEntity.setName(createCompanyDTO.getName());
        companyEntity.setUsername(createCompanyDTO.getUsername());
        companyEntity.setEmail(createCompanyDTO.getEmail());
        companyEntity.setWebsite(createCompanyDTO.getWebsite());
        companyEntity.setDescription(createCompanyDTO.getDescription());
        
        var password = passwordEncoder.encode(createCompanyDTO.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
    
}
