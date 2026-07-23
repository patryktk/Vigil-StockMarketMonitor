package pl.tkaczyk.sheetsservice.mapper;

import model.dto.TickerDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoogleSheetMapper {

    default List<TickerDto> mapToTickerList(List<List<Object>> rows) {
        if (rows == null) return Collections.emptyList();
        return rows.stream()
                .flatMap(Collection::stream)
                .map(String::valueOf)
                .filter(s -> !s.isBlank())
                .map(ticker -> TickerDto.builder()
                        .ticker(ticker)
                        .build())
                .toList();
    }
}
