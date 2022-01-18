package io.orep.ercotscraper;

public class ReportDefinitionNotFound extends Exception {

    public ReportDefinitionNotFound(String format, Object... objects) {
        super(String.format(format, objects));
    }

}
