package model.dto;

import model.events.StockSnapshotEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record StockSnapshotBatchEvent(
        UUID batchId,
        Instant scrapedAt,
        List<StockSnapshotEvent> snapshots
) {
}
