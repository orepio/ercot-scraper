package io.orep.ercotscraper.report.tdspesiidextract;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class EsiidRecord {

    @CsvBindByPosition(position = 0)
    private String esiid;

    @CsvBindByPosition(position = 1)
    private String address;

    @CsvBindByPosition(position = 2)
    private String addressOverflow;

    @CsvBindByPosition(position = 3)
    private String city;

    @CsvBindByPosition(position = 4)
    private String state;

    @CsvBindByPosition(position = 5)
    private String zipCode;

}
