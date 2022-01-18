package io.orep.ercotscraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipInputStream;

public abstract class AbstractScraper {

    private final ReportDefinitionScraper reportDefinitionScraper = new ReportDefinitionScraper();

    protected abstract int getReportTypeId();

    protected ReportDefinitionScraper getReportDefinitionScraper() {
        return this.reportDefinitionScraper;
    }

    protected ZipInputStream fetch(String url) {
        try {
            InputStream inputStream = new URL(url).openStream();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            zipInputStream.getNextEntry();
            return zipInputStream;
        } catch (IOException e) {
            throw new ErcotScraperException("Unable to fetch ZipInputStream from url[%s]", url);
        }
    }

}
