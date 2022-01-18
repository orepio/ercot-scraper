package io.orep.ercotscraper.report.damspp;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DamSettlementPointPrice {

    @CsvBindByPosition(position = 0)
    @CsvDate(value = "MM/dd/yyyy")
    private LocalDate deliveryDate;

    @CsvBindByPosition(position = 1)
    @CsvDate(value = "HH:mm")
    private LocalTime hourEnding;

    @CsvBindByPosition(position = 2)
    private String settlementPoint;

    @CsvBindByPosition(position = 3)
    private BigDecimal settlementPointPrice;

    @CsvCustomBindByPosition(position = 4, converter = DstFlagConverter.class)
    private Boolean dstFlag;

    public static class DstFlagConverter extends AbstractBeanField {
        @Override
        protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
            return s.equals("Y") ? Boolean.TRUE : Boolean.FALSE;
        }
    }

}
