package pl.tkaczyk.sheetsservice.messaging;

import lombok.RequiredArgsConstructor;
import model.dto.InvestDataDto;
import model.dto.StockSnapshotBatchEvent;
import model.events.DividendEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.tkaczyk.sheetsservice.mapper.EventMapper;
import pl.tkaczyk.sheetsservice.service.GoogleSheetsService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final ObjectMapper objectMapper;
    private final GoogleSheetsService sheetsWriterService;
    private final EventMapper eventMapper;

    @KafkaListener(topics = "vigil.scraping.stock-events", groupId = "sheets-service")
    public void listen(ConsumerRecord<String, String> record) {
        String eventType = new String(record.headers().lastHeader("event-type").value());

        switch (eventType) {
            case "StockSnapshotBatchEvent" -> {
                StockSnapshotBatchEvent batch = objectMapper.readValue(record.value(), StockSnapshotBatchEvent.class);
                List<InvestDataDto> list = batch.snapshots().stream().map(eventMapper::toObject).toList();
                sheetsWriterService.writeData(list);
            }
            case "DividendEvent" -> {
                DividendEvent event = objectMapper.readValue(record.value(), DividendEvent.class);
                //Todo: dodać zapis
            }
            default -> throw new IllegalStateException("Unknown event type: " + eventType);
        }
    }
}
