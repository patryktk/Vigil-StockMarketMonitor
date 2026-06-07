package pl.tkaczyk.scraperservice.model.dto;

import lombok.Builder;
import org.jsoup.nodes.Document;

@Builder
public record StrefaInwestorowDocument(Document dividendAnnouncementDocument) {
}
