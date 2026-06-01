package pl.tkaczyk.scraperservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tkaczyk.scraperservice.model.Company;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findCompanyByTicker(String ticker);
}
