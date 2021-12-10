package io.orep.ercotscraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ReportDefinitionScraper {

    public List<ReportDefinition> fetchReportDefinitions(int reportTypeId) {
        try {
            Document doc = Jsoup.connect(this.createUrl(reportTypeId)).get();
            Elements aElements = doc.select("a");
            List<ReportDefinition> reportDefinitions = aElements.stream().map(element -> {
                ReportDefinition reportDefinition = new ReportDefinition();
                reportDefinition.setTitle(element.parent().parent().parent().child(0).text());
                reportDefinition.setUrl(Constants.BASE_URL + element.attr("href"));
                return reportDefinition;
            }).collect(Collectors.toList());
            log.debug("Fetched {} ReportDefinitions for ReportTypeId[{}]", reportDefinitions.size(), reportTypeId);
            return reportDefinitions;
        } catch (IOException e) {
            throw new ErcotScraperException("Unable to fetch HTML for reportTypeId " + reportTypeId, e);
        }
    }

    private String createUrl(int reportTypeId) {
        return String.format(Constants.REPORT_URL, reportTypeId);
    }

}
