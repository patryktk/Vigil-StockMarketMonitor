package pl.tkaczyk.sheetsservice.model.dto;

import lombok.Builder;

@Builder
public record TickerDto(String ticker) {
}
