package pl.tkaczyk.sheetsservice.mapper;

import org.mapstruct.Mapper;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoogleSheetMapper {

    default List<TickerDto> mapToTickerList(List<List<Object>> rows) {
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
