package io.orep.ercotscraper.report.damspp;

import com.opencsv.bean.CsvToBeanBuilder;
import io.orep.ercotscraper.AbstractScraper;
import io.orep.ercotscraper.Format;
import io.orep.ercotscraper.ReportDefinition;
import io.orep.ercotscraper.ReportDefinitionNotFound;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
public class DamSettlementPointPriceScraper extends AbstractScraper {

    public final static int REPORT_TYPE_ID = 12331;

    public List<DamSettlementPointPrice> fetch(LocalDate date) throws ReportDefinitionNotFound {
        ReportDefinition reportDefinition = this.getReportDefinitionScraper().fetchReportDefinition(getReportTypeId(), date, Format.CSV.name().toLowerCase());
        ZipInputStream zipInputStream = fetch(reportDefinition.getUrl());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipInputStream));
        List<DamSettlementPointPrice> records = new CsvToBeanBuilder(bufferedReader)
                .withType(DamSettlementPointPrice.class)
                .withSkipLines(1)
                .build()
                .parse();
        return records;
    }

    @Override
    public int getReportTypeId() {
        return REPORT_TYPE_ID;
    }

}
