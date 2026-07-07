package pl.tkaczyk.scraperservice.service;

import org.jsoup.nodes.Document;

public interface HtmlDocumentFetcher {

    Document getDocument(String html);
}
