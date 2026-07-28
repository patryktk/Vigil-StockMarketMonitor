package pl.tkaczyk.scraperservice.messaging;

import lombok.RequiredArgsConstructor;
import model.dto.StockSnapshotBatchEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventPublisher {

    public static final String TOPIC = "vigil.scraping.stock-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSnapshotBatch(StockSnapshotBatchEvent event) {
        var message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, event.batchId().toString())
                .setHeader("event-type", "StockSnapshotBatchEvent")
                .build();
        kafkaTemplate.send(message);
    }
}
