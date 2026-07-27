package pl.tkaczyk.scraperservice.messaging;

import lombok.RequiredArgsConstructor;
import model.dto.InvestDataDto;
import model.events.DividendEvent;
import model.events.StockSnapshotEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockEventPublisher {

    public static final String TOPIC = "vigil.scraping.stock-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSnapshot(StockSnapshotEvent event) {
        var message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, event.ticker())
                .setHeader("event-type", "StockSnapshotEvent")
                .build();
        kafkaTemplate.send(message);
    }

    public void publishDividend(DividendEvent event) {
        var message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, event.ticker())
                .setHeader("event-type", "DividendEvent")
                .build();
        kafkaTemplate.send(message);
    }



    public void publishStockInfo(List<InvestDataDto> event) {
        var message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY,)
                .setHeader("event-type", "List<InvestData>")
                .build();
        kafkaTemplate.send(message);
    }
}
