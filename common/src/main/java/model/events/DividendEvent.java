package model.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DividendEvent(
        String ticker,
        BigDecimal amount,
        LocalDate exDividendDate,
        Instant scrapedAt
) {}