package pl.tkaczyk.scraperservice.mapper;

import org.mapstruct.Mapper;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.dto.CompanyDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyDTO dto);
    CompanyDTO toDto(Company entity);
}
