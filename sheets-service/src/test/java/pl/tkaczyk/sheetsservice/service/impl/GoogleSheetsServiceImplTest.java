package pl.tkaczyk.sheetsservice.service.impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import model.dto.TickerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaczyk.sheetsservice.config.GoogleSheetsProperties;
import pl.tkaczyk.sheetsservice.mapper.GoogleSheetMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleSheetsServiceImplTest {

    // Używamy RETURNS_DEEP_STUBS, aby uniknąć mockowania każdego zagnieżdżenia API Google z osobna
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Sheets sheetsClient;
    @Mock
    private GoogleSheetsProperties googleSheetsProperties;
    @Mock
    private GoogleSheetMapper googleSheetMapper;
    @InjectMocks
    private GoogleSheetsServiceImpl googleSheetsService;

    @Test
    @DisplayName("Should read ticker")
    void shouldReadTicker() throws IOException{
        String spreadsheetId = "test-id";
        String range = "Sheet1!A1:A10";
        List<List<Object>> mockValues = List.of(List.of("AAPL"));
        List<TickerDto> expectedTickers = List.of(new TickerDto("AAPL"));

        ValueRange valueRange = new ValueRange().setValues(mockValues);

        when(googleSheetsProperties.getSpreadsheetId()).thenReturn(spreadsheetId);
        when(googleSheetsProperties.getTickerRange()).thenReturn(range);

        when(sheetsClient.spreadsheets().values().get(spreadsheetId, range).execute())
                .thenReturn(valueRange);

        when(googleSheetMapper.mapToTickerList(mockValues)).thenReturn(expectedTickers);

        List<TickerDto> result = googleSheetsService.readTicker();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).ticker()).isEqualTo("AAPL");

        verify(googleSheetMapper).mapToTickerList(mockValues);
    }

    @Test
    void shouldReturnNullWhenGoogleApiExceptionIsThrown() throws IOException {
        // given
        when(googleSheetsProperties.getSpreadsheetId()).thenReturn("test-id");
        when(googleSheetsProperties.getTickerRange()).thenReturn("range");

        // Symulacja błędu z API Google
        when(sheetsClient.spreadsheets().values().get(any(), any()).execute())
                .thenThrow(new IOException("Google API Error"));

        // when
        List<TickerDto> result = googleSheetsService.readTicker();

        // then
        assertThat(result).isNull(); // Zgodnie z obecną implementacją catch(Exception e)

        // Weryfikacja, że w przypadku błędu mapper NIE zostaje wywołany
        verifyNoInteractions(googleSheetMapper);
    }
}