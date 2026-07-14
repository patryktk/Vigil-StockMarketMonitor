package pl.tkaczyk.sheetsservice.service;

import pl.tkaczyk.sheetsservice.model.dto.StockSnapshotDto;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;

import java.util.List;

public interface GoogleSheetsService {
    List<TickerDto> readTicker();

    void writeData(List<StockSnapshotDto> payload);
}
