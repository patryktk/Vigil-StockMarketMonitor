package pl.tkaczyk.scraperservice.service;

import model.dto.StockSnapshotDto;
import pl.tkaczyk.scraperservice.model.dto.BiznesRadarDocuments;

public interface BiznesRadarParser {

    StockSnapshotDto parse(BiznesRadarDocuments documents);
}
