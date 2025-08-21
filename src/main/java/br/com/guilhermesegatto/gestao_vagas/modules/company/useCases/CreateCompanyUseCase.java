package br.com.guilhermesegatto.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guilhermesegatto.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.ComppanyEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.repositories.CompanyRepository;


@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;
    public ComppanyEntity execute(ComppanyEntity comppanyEntity) {
       
        this.companyRepository.findByUsernameOrEmail(comppanyEntity.getUsername(), comppanyEntity.getEmail())
            .ifPresent((user) -> {
               throw new UserFoundException();
            });

        return this.companyRepository.save(comppanyEntity);
    }
    
}
