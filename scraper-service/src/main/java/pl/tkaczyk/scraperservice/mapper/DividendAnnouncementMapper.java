package pl.tkaczyk.scraperservice.mapper;

import org.mapstruct.Mapper;
import pl.tkaczyk.scraperservice.model.DividendAnnouncement;
import pl.tkaczyk.scraperservice.model.dto.DividendAnnouncementDto;

@Mapper(componentModel = "spring")
public interface DividendAnnouncementMapper {

    DividendAnnouncement toEntity(DividendAnnouncementDto dto);
}
