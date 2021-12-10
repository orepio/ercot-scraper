package io.orep.ercotscraper;

import io.orep.ercotscraper.tdspesiidextract.EsiidRecord;
import io.orep.ercotscraper.tdspesiidextract.TdspEsiidExtractScraper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TdspEsiidExtractTest {

    @Test
    void extractLatest() {
        TdspEsiidExtractScraper tdspEsiidExtractScraper = new TdspEsiidExtractScraper();
        List<EsiidRecord> esiidRecords = tdspEsiidExtractScraper.fetch(Tdsp.CENTERPOINT, LocalDate.now().minusDays(1), TdspEsiidExtractScraper.ReportType.DAILY);
        assertThat(esiidRecords).isNotEmpty();
    }

}
