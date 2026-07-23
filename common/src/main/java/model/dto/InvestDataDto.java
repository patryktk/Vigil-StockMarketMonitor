package model.dto;

import lombok.Builder;

@Builder
public record InvestDataDto(DividendAnnouncementDto dividendAnnouncementDto, StockSnapshotDto stockSnapshot) {
}
