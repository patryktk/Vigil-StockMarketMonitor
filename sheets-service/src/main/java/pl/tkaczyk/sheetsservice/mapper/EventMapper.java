package pl.tkaczyk.sheetsservice.mapper;

import model.dto.InvestDataDto;
import model.events.StockSnapshotEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    StockSnapshotEvent toEvent(InvestDataDto dto);

    InvestDataDto toObject(StockSnapshotEvent event);
}
