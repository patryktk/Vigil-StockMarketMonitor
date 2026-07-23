package pl.tkaczyk.sheetsservice.service.impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.InvestDataDto;
import model.dto.StockSnapshotDto;
import model.dto.TickerDto;
import org.springframework.stereotype.Service;
import pl.tkaczyk.sheetsservice.config.GoogleSheetsProperties;
import pl.tkaczyk.sheetsservice.mapper.GoogleSheetMapper;
import pl.tkaczyk.sheetsservice.mapper.StockDataSheetRowMapper;
import pl.tkaczyk.sheetsservice.model.dto.StockDataSheetRow;
import pl.tkaczyk.sheetsservice.service.GoogleSheetsService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleSheetsServiceImpl implements GoogleSheetsService {


    private final Sheets sheetsClient;
    private final GoogleSheetsProperties googleSheetsProperties;
    private final GoogleSheetMapper googleSheetMapper;
    private final StockDataSheetRowMapper stockDataMapper;

    @Override
    public List<TickerDto> readTicker() {

        try {
            ValueRange execute = sheetsClient.spreadsheets().values().get(googleSheetsProperties.getSpreadsheetId(), googleSheetsProperties.getTickerRange()).execute();
            List<List<Object>> tickersValues = execute.getValues();

            return googleSheetMapper.mapToTickerList(tickersValues);

        }catch (IOException e){
            log.error("Error while reading ticker from Google Sheets", e);
        }
        return null;
    }

    @Override
    public void writeData(List<InvestDataDto> payload) {

        List<StockSnapshotDto> stockList = payload.stream().map(InvestDataDto::stockSnapshot).toList();


        int targetColumnIndex = 0;
        try {

            List<List<Object>> columns = stockList.stream()
                    .map(stockDataMapper::toStockDataSheetRow)
                    .map(StockDataSheetRow::toColumnList)
                    .toList();

            String startCol = columnLabelFromIndex(targetColumnIndex);
            String endCol = columnLabelFromIndex(targetColumnIndex + payload.size());

            int fieldsCount = columns.getFirst().size();
            int endRow = googleSheetsProperties.getSaveRangeStartColNumber() + fieldsCount;

            String range = googleSheetsProperties.getSaveRangeName()
                    + startCol
                    + googleSheetsProperties.getSaveRangeStartColNumber()
                    + ":"
                    + endCol
                    + endRow;

            ValueRange body = new ValueRange()
                    .setMajorDimension("COLUMNS")
                    .setValues(columns);

            sheetsClient.spreadsheets().values()
                    .update(googleSheetsProperties.getSpreadsheetId(), range, body)
                    .setValueInputOption("USER_ENTERED")
                    .execute();

        } catch (Exception e) {
            log.error("Error while writing data to Google Sheets", e);
        }
    }

    private String columnLabelFromIndex(int indexStartingAtC) {
        int baseIndex = indexStartingAtC + 2; // 0->2 which is 'C' (A=0)
        StringBuilder sb = new StringBuilder();
        while (baseIndex >= 0) {
            int rem = baseIndex % 26;
            sb.insert(0, (char) ('A' + rem));
            baseIndex = (baseIndex / 26) - 1;
        }
        return sb.toString();
    }
}
