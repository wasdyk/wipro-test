package org.example.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDataReader {

    public List<InstrumentRecord> readData(String fileName) {
        List<InstrumentRecord> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String instrumentName = parts[0];
                LocalDate date = LocalDate.parse(parts[1], formatter);
                BigDecimal value = new BigDecimal(parts[2]);
                records.add(new InstrumentRecord(instrumentName, date, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
}

