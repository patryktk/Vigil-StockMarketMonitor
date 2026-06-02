package pl.tkaczyk.scraperservice.model.dto;

import lombok.Builder;
import org.jsoup.nodes.Document;

@Builder
public record BiznesRadarDocuments(
        Document financialData,
        Document rentData,
        Document debtData,
        Document flowData,
        Document bilansData,
        Document raportBiznes,
        Document raportFlow
) {
}
