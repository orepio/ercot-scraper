package io.orep.ercotscraper;

public enum Format {

    CSV, XML, UNKNOWN;

    public static Format fromTitleString(String value) {
        return switch (value) {
            case "csv" -> Format.CSV;
            case "xml" -> Format.XML;
            default -> Format.UNKNOWN;
        };
    }

}
