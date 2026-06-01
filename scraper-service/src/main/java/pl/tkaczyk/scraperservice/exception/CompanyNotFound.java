package pl.tkaczyk.scraperservice.exception;

public class CompanyNotFound extends RuntimeException {
    public CompanyNotFound(String message ) {
        super(message);
    }
}
