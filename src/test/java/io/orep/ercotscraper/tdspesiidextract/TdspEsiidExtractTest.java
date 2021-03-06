package io.orep.ercotscraper.tdspesiidextract;

import io.orep.ercotscraper.ReportDefinitionNotFound;
import io.orep.ercotscraper.Tdsp;
import io.orep.ercotscraper.report.tdspesiidextract.EsiidRecord;
import io.orep.ercotscraper.report.tdspesiidextract.TdspEsiidExtractScraper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TdspEsiidExtractTest {

    @Test
    void extractLatest() throws ReportDefinitionNotFound {
        TdspEsiidExtractScraper tdspEsiidExtractScraper = new TdspEsiidExtractScraper();
        List<EsiidRecord> esiidRecords = tdspEsiidExtractScraper.fetch(Tdsp.CENTERPOINT, LocalDate.now().minusDays(1), TdspEsiidExtractScraper.ReportType.DAILY);
        assertThat(esiidRecords).isNotEmpty();
    }

}
