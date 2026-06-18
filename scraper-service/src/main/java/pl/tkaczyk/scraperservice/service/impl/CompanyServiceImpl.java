package pl.tkaczyk.scraperservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.mapper.CompanyMapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.dto.CompanyDTO;
import pl.tkaczyk.scraperservice.repository.CompanyRepository;
import pl.tkaczyk.scraperservice.service.CompanyService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDTO addCompany(CompanyDTO companyDTO) {
        Company save = companyRepository.save(companyMapper.toEntity(companyDTO));
        return companyMapper.toDto(save);
    }

    @Override
    public Company findCompanyByTickerOrCreate(String ticker) {
        return companyRepository.findByTicker(ticker).orElseGet(() -> companyRepository.save(new Company(null, ticker, ticker)));
    }
}
