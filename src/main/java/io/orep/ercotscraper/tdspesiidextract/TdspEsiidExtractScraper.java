package io.orep.ercotscraper.tdspesiidextract;

import com.opencsv.bean.CsvToBeanBuilder;
import io.orep.ercotscraper.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class TdspEsiidExtractScraper implements Scraper {

    public enum ReportType {

        DAILY("DAILY"), FULL("FUL");

        private final String filter;

        ReportType(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }

    }

    public final static int REPORT_TYPE_ID = 203;
    private final ReportDefinitionScraper reportDefinitionScraper = new ReportDefinitionScraper();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<EsiidRecord> fetch(Tdsp tdsp, LocalDate date, ReportType reportType) {
        try {
            List<ReportDefinition> reportDefinitions = reportDefinitionScraper.fetchReportDefinitions(REPORT_TYPE_ID);
            ReportDefinition reportDefinition = reportDefinitions.stream()
                    .filter(rd -> rd.getTitle().contains(this.createNameFilter(tdsp)) &&
                            rd.getTitle().contains(reportType.getFilter()) &&
                            rd.getTitle().contains(date.format(dateTimeFormatter)))
                    .findFirst()
                    .orElseThrow(() -> new ErcotScraperException("No Report found for tdsp[%], date[%s] and reportType[%s]", tdsp, date, reportType));
            InputStream inputStream = new URL(reportDefinition.getUrl()).openStream();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipInputStream));
            List<EsiidRecord> esiidRecords = new CsvToBeanBuilder(bufferedReader)
                    .withType(EsiidRecord.class)
                    .build()
                    .parse();
            return esiidRecords;
        } catch (IOException e) {
            throw new ErcotScraperException(e.getMessage(), e);
        }
    }

    private String createNameFilter(Tdsp tdsp) {
        switch (tdsp) {
            case CENTERPOINT: return "CENTERPOINT";
            case ONCOR: return "ONCOR_ELEC";
            case SWEPCO: return "SWEPCO_ENERG";
            case NUECES: return "NUECES_ELEC";
            case TNMP: return "TNMP";
            case AEP_CENTRAL: return "AEP_CENTRAL";
            case AEP_NORTH: return "AEP_NORTH";
            case ENTERGY: return "ENTERGY_GULF";
            case SHARYLAND_MCALLEN: return "SHARYLAND_MCALLEN";
            case SHARYLAND_UTILITIES: return "SHARYLAND_UTILITIES";
            case AEP_TEXAS_SP: return "AEP_TEXAS_SP";
            default: throw new IllegalArgumentException("No mapping for Tdsp: " + tdsp);
        }
    }

    @Override
    public int getReportTypeId() {
        return REPORT_TYPE_ID;
    }

}
