package org.example.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessor {

    private final DatabaseService databaseService;

    public DataProcessor() {
        this.databaseService = new DatabaseService();
    }

    private BigDecimal applyMultiplier(String instrumentName, BigDecimal value) {
        BigDecimal multiplier = databaseService.getMultiplierForInstrument(instrumentName);
        return value.multiply(multiplier);
    }

    private double calculateStandardDeviation(List<Double> values) {
        if (values.isEmpty()) {
            return 0.0;
        }

        DoubleSummaryStatistics stats = values.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();
        double mean = stats.getAverage();
        double variance = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average().orElse(0.0);

        return Math.sqrt(variance);
    }

    public BigDecimal calculateMeanForInstrument1(List<InstrumentRecord> records) {
        List<BigDecimal> values = records.stream()
                .filter(record -> "INSTRUMENT1".equals(record.instrumentName()))
                .map(InstrumentRecord::value)
                .collect(Collectors.toList());

        BigDecimal mean = calculateMean(values);
        return applyMultiplier("INSTRUMENT1", mean);
    }

    private BigDecimal calculateMean(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = values.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(values.size()), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateMeanForInstrument2November2014(List<InstrumentRecord> records) {
        List<BigDecimal> values = records.stream()
                .filter(record -> "INSTRUMENT2".equals(record.instrumentName()))
                .filter(record -> record.date().getMonth() == Month.NOVEMBER && record.date().getYear() == 2014)
                .map(InstrumentRecord::value)
                .collect(Collectors.toList());

        BigDecimal mean = calculateMean(values);
        return applyMultiplier("INSTRUMENT2", mean);
    }

    public double calculateStandardDeviationForInstrument3(List<InstrumentRecord> records) {
        List<Double> values = records.stream()
                .filter(record -> "INSTRUMENT3".equals(record.instrumentName()))
                .map(record -> record.value().doubleValue())
                .collect(Collectors.toList());

        double stdDev = calculateStandardDeviation(values);
        return stdDev * databaseService.getMultiplierForInstrument("INSTRUMENT3").doubleValue();
    }

    public BigDecimal sumLatest10EntriesForOtherInstruments(List<InstrumentRecord> records, String instrumentName) {
        BigDecimal sum = records.stream()
                .filter(record -> instrumentName.equals(record.instrumentName()))
                .sorted(Comparator.comparing(InstrumentRecord::date).reversed())
                .limit(10)
                .map(InstrumentRecord::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return applyMultiplier(instrumentName, sum);
    }

}
