package io.orep.ercotscraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class ReportDefinitionScraper {

    private final DateTimeFormatter titleDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<ReportDefinition> fetchReportDefinitions(int reportTypeId) {
        String url = this.createUrl(reportTypeId);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ErcotScraperException("Unable to connect to url: %s", url);
        }
        Elements aElements = doc.select("a");
        List<ReportDefinition> reportDefinitions = aElements.stream().map(element -> {
            String title = element.parent().parent().parent().child(0).text();
            String reportUrl = Constants.BASE_URL + element.attr("href");
            LocalDate date = this.getDateFromTitle(title);
            Format format = this.getFormatFromTitle(title);
            ReportDefinition reportDefinition = new ReportDefinition(reportTypeId, title, reportUrl, date, format);
            return reportDefinition;
        }).collect(Collectors.toList());
        log.debug("Fetched %s ReportDefinitions for reportTypeId[%s]", reportDefinitions.size(), reportTypeId);
        return reportDefinitions;
    }

    public ReportDefinition fetchReportDefinition(int reportTypeId, LocalDate date, String... contains) throws ReportDefinitionNotFound {
        List<ReportDefinition> reportDefinitionList = this.fetchReportDefinitions(reportTypeId);
        Predicate<ReportDefinition> predicates = this.dateFilter(date);
        for (String c: contains) {
            predicates = predicates.and(titleFilter(c));
        }
        reportDefinitionList = reportDefinitionList.stream().filter(predicates).collect(Collectors.toList());
        if (reportDefinitionList.isEmpty()) {
            throw new ReportDefinitionNotFound("No ReportDefinition found for reportTypeId[%s], date[%s], contains[%s]", reportTypeId, date, contains);
        } else if (reportDefinitionList.size() > 1) {
            throw new ErcotScraperException("More than 1 ReportDefinition found for reportTypeId[%s], date[%s], contains[%s]", reportTypeId, date, contains);
        }
        return reportDefinitionList.get(0);
    }

    private String createUrl(int reportTypeId) {
        return String.format(Constants.REPORT_URL_FORMAT, reportTypeId);
    }

    private LocalDate getDateFromTitle(String title) {
        return LocalDate.parse(title.split("\\.")[3], titleDateTimeFormatter);
    }

    private Format getFormatFromTitle(String title) {
        String temp = title.split("\\.")[5].split("_")[1];
        return Format.fromTitleString(temp);
    }

    private Predicate<ReportDefinition> dateFilter(LocalDate date) {
        return reportDefinition -> reportDefinition.getDate().equals(date);
    }

    private Predicate<ReportDefinition> titleFilter(String contains) {
        return reportDefinition -> reportDefinition.getTitle().contains(contains);
    }

}
