package io.orep.ercotscraper;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ReportDefinition {

    private final int reportTypeId;
    private final String title;
    private final String url;
    private final LocalDate date;
    private final Format format;

}
