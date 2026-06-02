package pl.tkaczyk.scraperservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tkaczyk.scraperservice.model.Company;
import pl.tkaczyk.scraperservice.model.StockSnapshot;
import pl.tkaczyk.scraperservice.model.dto.StockSnapshotDto;

@Mapper(componentModel = "spring")
public interface StockMapper {


//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "company", source = "company")
    StockSnapshot toEntity(StockSnapshotDto dto);

}
