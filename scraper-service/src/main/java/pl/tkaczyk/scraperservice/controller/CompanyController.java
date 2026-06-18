package pl.tkaczyk.scraperservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tkaczyk.scraperservice.model.dto.CompanyDTO;
import pl.tkaczyk.scraperservice.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @PostMapping("/addCompany")
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody @Valid CompanyDTO companyDTO){
         return ResponseEntity.status(HttpStatus.CREATED).body(companyService.addCompany(companyDTO));
    }


}
