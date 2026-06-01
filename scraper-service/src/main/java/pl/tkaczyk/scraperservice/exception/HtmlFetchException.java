package pl.tkaczyk.scraperservice.exception;

public class HtmlFetchException extends RuntimeException {
    public HtmlFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
