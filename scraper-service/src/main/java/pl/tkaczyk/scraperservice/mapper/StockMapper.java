package pl.tkaczyk.scraperservice.mapper;

import model.dto.StockSnapshotDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;

@Mapper(componentModel = "spring")
public interface StockMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", source = "company")
    StockSnapshot toEntity(StockSnapshotDto dto, Company company);

    StockSnapshotDto toDto(StockSnapshot entity);
}
