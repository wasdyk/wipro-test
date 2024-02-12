package org.example.app;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InstrumentRecord(String instrumentName, LocalDate date, BigDecimal value) {

    @Override
    public String toString() {
        return "InstrumentRecord{" +
                "instrumentName='" + instrumentName + '\'' +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
