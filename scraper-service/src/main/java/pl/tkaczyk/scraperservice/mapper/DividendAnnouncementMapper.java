package pl.tkaczyk.scraperservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.DividendAnnouncement;
import model.dto.DividendAnnouncementDto;

@Mapper(componentModel = "spring")
public interface DividendAnnouncementMapper {

    @Mapping(target = "company", source = "company")
    DividendAnnouncement toEntity(DividendAnnouncementDto dto, Company company);

    DividendAnnouncementDto toDto(DividendAnnouncement entity);
}
