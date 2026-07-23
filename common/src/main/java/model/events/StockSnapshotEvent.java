package model.events;

import java.math.BigDecimal;
import java.time.Instant;

public record StockSnapshotEvent(
        String ticker
//        BigDecimal price,
//        BigDecimal changePercent,
//        Instant scrapedAt
) {}
