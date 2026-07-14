package pl.tkaczyk.sheetsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tkaczyk.sheetsservice.model.dto.TickerDto;
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

}
