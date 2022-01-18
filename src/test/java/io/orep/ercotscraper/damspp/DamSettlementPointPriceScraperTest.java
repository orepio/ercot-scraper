package io.orep.ercotscraper.damspp;

import io.orep.ercotscraper.ReportDefinitionNotFound;
import io.orep.ercotscraper.report.damspp.DamSettlementPointPrice;
import io.orep.ercotscraper.report.damspp.DamSettlementPointPriceScraper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DamSettlementPointPriceScraperTest {

    @Test
    public void fetchYesterday() throws ReportDefinitionNotFound {
        DamSettlementPointPriceScraper scraper = new DamSettlementPointPriceScraper();
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        LocalDate yesterday = twoDaysAgo.plusDays(1);
        List<DamSettlementPointPrice> list = scraper.fetch(twoDaysAgo);
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getSettlementPoint()).isNotNull();
        assertThat(list.get(0).getSettlementPointPrice()).isNotNull();
        assertThat(list.get(0).getDeliveryDate()).isEqualTo(yesterday);
        assertThat(list.get(0).getHourEnding()).isNotNull();
        assertThat(list.get(0).getDstFlag()).isNotNull();
    }

}
