package br.com.guilhermesegatto.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import br.com.guilhermesegatto.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.guilhermesegatto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermesegatto.gestao_vagas.modules.company.useCases.CreateJobUseCase;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        return this.createJobUseCase.execute(createJobDTO, UUID.fromString(companyId.toString()));
    }
}
