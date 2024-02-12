import org.example.app.DataProcessor;
import org.example.app.InstrumentRecord;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DataProcessorTest {

    private List<InstrumentRecord> records;
    private DataProcessor dataProcessor;

    @Before
    public void setUp() {
        records = new ArrayList<>();
        dataProcessor = new DataProcessor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        records.add(new InstrumentRecord("INSTRUMENT1", LocalDate.parse("01-Jan-1996", formatter), new BigDecimal("2.4655")));
        records.add(new InstrumentRecord("INSTRUMENT1", LocalDate.parse("02-Jan-1996", formatter), new BigDecimal("2.4685")));
        records.add(new InstrumentRecord("INSTRUMENT2", LocalDate.parse("01-Nov-2014", formatter), new BigDecimal("1.0000")));
        records.add(new InstrumentRecord("INSTRUMENT2", LocalDate.parse("02-Nov-2014", formatter), new BigDecimal("1.5000")));
        records.add(new InstrumentRecord("INSTRUMENT3", LocalDate.parse("01-Jan-1996", formatter), new BigDecimal("2.0")));
        records.add(new InstrumentRecord("INSTRUMENT3", LocalDate.parse("02-Jan-1996", formatter), new BigDecimal("4.0")));
        for (int i = 1; i <= 15; i++) {
            String day = String.format("%02d", i);
            records.add(new InstrumentRecord("INSTRUMENT4", LocalDate.parse(day + "-Jan-1996", formatter), new BigDecimal("1.0000")));
        }
    }

    @Test
    public void testCalculateMeanForInstrument1() {
        BigDecimal expectedMean = new BigDecimal("2.4670");
        BigDecimal calculatedMean = dataProcessor.calculateMeanForInstrument1(records);
        assertEquals(0, expectedMean.compareTo(calculatedMean));
    }

    @Test
    public void testCalculateMeanForInstrument2November2014() {
        BigDecimal expectedMean = new BigDecimal("1.2500");
        BigDecimal calculatedMean = dataProcessor.calculateMeanForInstrument2November2014(records);
        assertEquals(0, expectedMean.compareTo(calculatedMean));
    }


    @Test
    public void testCalculateStandardDeviationForInstrument3() {
        double expectedStdDev = 1.0;
        double calculatedStdDev = dataProcessor.calculateStandardDeviationForInstrument3(records);
        assertEquals(expectedStdDev, calculatedStdDev, 0.0001);
    }

    @Test
    public void testSumLatest10EntriesForOtherInstruments() {
        BigDecimal expectedSum = new BigDecimal("10.0000");
        BigDecimal calculatedSum = dataProcessor.sumLatest10EntriesForOtherInstruments(records, "INSTRUMENT4");
        assertEquals(0, expectedSum.compareTo(calculatedSum));
    }
}
