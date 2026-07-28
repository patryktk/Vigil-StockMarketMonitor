package model.events;

import model.dto.DividendAnnouncementDto;
import model.dto.StockSnapshotDto;

import java.math.BigDecimal;
import java.time.Instant;

public record StockSnapshotEvent(
        DividendAnnouncementDto dividendAnnouncementDto,
        StockSnapshotDto stockSnapshot
) {}
