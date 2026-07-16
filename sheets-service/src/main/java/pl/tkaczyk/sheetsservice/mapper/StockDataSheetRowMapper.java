package pl.tkaczyk.sheetsservice.mapper;

import model.dto.StockSnapshotDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import pl.tkaczyk.sheetsservice.model.dto.StockDataSheetRow;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StockDataSheetRowMapper {

    StockDataSheetRow toStockDataSheetRow(StockSnapshotDto source);

}
