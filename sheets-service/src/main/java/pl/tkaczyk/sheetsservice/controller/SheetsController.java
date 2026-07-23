package pl.tkaczyk.sheetsservice.controller;

import lombok.RequiredArgsConstructor;
import model.dto.InvestDataDto;
import model.dto.TickerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaczyk.sheetsservice.service.GoogleSheetsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sheets")
public class SheetsController {

    private final GoogleSheetsService googleSheetsService;

    @GetMapping("/read")
    public ResponseEntity<List<TickerDto>> readTicker(){
        return ResponseEntity.ok(googleSheetsService.readTicker());
    }

    @PostMapping("/write")
    public ResponseEntity<Void> writeData(@RequestBody List<InvestDataDto> payload) {
        googleSheetsService.writeData(payload);
        return ResponseEntity.ok().build();
    }

}
