package pl.tkaczyk.scraperservice.service;

import jakarta.validation.Valid;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.dto.CompanyDTO;

public interface CompanyService {
    CompanyDTO addCompany(@Valid CompanyDTO companyDTO);

    Company findCompanyByTickerOrCreate(String ticker);
}
