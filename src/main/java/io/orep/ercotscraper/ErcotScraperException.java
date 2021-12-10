package io.orep.ercotscraper;

public class ErcotScraperException extends RuntimeException {

    public ErcotScraperException(String message) {
        super(message);
    }

    public ErcotScraperException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public ErcotScraperException(String message, Throwable cause) {
        super(message, cause);
    }

}
