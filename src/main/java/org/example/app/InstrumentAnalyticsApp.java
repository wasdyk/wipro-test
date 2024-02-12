package org.example.app;

import org.example.app.config.DatabaseConfig;

import java.math.BigDecimal;
import java.util.List;

public class InstrumentAnalyticsApp {
    public static void main(String[] args) {
        DatabaseConfig.setUpDatabase();

        DataProcessor processor = new DataProcessor();
        InstrumentDataReader reader = new InstrumentDataReader();
        List<InstrumentRecord> records = reader.readData("example_input.txt");

        BigDecimal meanInstrument1 = processor.calculateMeanForInstrument1(records);
        System.out.println("Mean for INSTRUMENT1: " + meanInstrument1);

        BigDecimal meanInstrument2Nov2014 = processor.calculateMeanForInstrument2November2014(records);
        System.out.println("Mean for INSTRUMENT2 in November 2014: " + meanInstrument2Nov2014);

        double stdDevInstrument3 = processor.calculateStandardDeviationForInstrument3(records);
        System.out.println("Standard Deviation for INSTRUMENT3: " + stdDevInstrument3);

        BigDecimal sumLatest10Instrument4 = processor.sumLatest10EntriesForOtherInstruments(records, "INSTRUMENT4");
        System.out.println("Sum of the latest 10 entries for INSTRUMENT4: " + sumLatest10Instrument4);

    }
}


