package pl.tkaczyk.sheetsservice.exception;

public class GoogleSheetsIntegrationException extends RuntimeException {
    public GoogleSheetsIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
