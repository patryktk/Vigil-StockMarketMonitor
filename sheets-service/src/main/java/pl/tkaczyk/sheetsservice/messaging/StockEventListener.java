package pl.tkaczyk.sheetsservice.messaging;

import lombok.RequiredArgsConstructor;
import model.events.DividendEvent;
import model.events.StockSnapshotEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "vigil.scraping.stock-events", groupId = "sheets-service")
    public void listen(ConsumerRecord<String, String> record) {
        String eventType = new String(record.headers().lastHeader("event-type").value());

        switch (eventType) {
            case "StockSnapshotEvent" -> {
                StockSnapshotEvent event = objectMapper.readValue(record.value(), StockSnapshotEvent.class);
                //Todo: dodać zapis
                System.out.println("StockSnapshotEvent: " + event.ticker());
            }
            case "DividendEvent" -> {
                DividendEvent event = objectMapper.readValue(record.value(), DividendEvent.class);
                //Todo: dodać zapis
            }
            default -> throw new IllegalStateException("Unknown event type: " + eventType);
        }
    }
}
