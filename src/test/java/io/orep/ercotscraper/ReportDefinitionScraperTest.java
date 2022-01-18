package io.orep.ercotscraper;

import io.orep.ercotscraper.report.damspp.DamSettlementPointPriceScraper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReportDefinitionScraperTest {
    
    private ReportDefinitionScraper reportDefinitionScraper = new ReportDefinitionScraper();
    
    @Test
    public void badReportId() {
        assertThrows(ErcotScraperException.class, () -> {
            reportDefinitionScraper.fetchReportDefinitions(Integer.MAX_VALUE);
        });
    }

    @Test
    public void multipleReports() {
        assertThrows(ErcotScraperException.class, () -> {
            reportDefinitionScraper.fetchReportDefinition(DamSettlementPointPriceScraper.REPORT_TYPE_ID, LocalDate.now().minusDays(2));
        });
    }
    
}
