package pl.tkaczyk.scraperservice.model.dto;

import jakarta.validation.constraints.NotNull;

public record CompanyDTO(@NotNull String ticker, String name) {
}
