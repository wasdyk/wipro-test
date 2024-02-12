package org.example.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseService {

    public BigDecimal getMultiplierForInstrument(String instrumentName) {
        BigDecimal multiplier = BigDecimal.ONE; // Default multiplier
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             PreparedStatement pstmt = conn.prepareStatement("SELECT MULTIPLIER FROM INSTRUMENT_PRICE_MODIFIER WHERE NAME = ?")) {

            pstmt.setString(1, instrumentName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                multiplier = rs.getBigDecimal("MULTIPLIER");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return multiplier;
    }
}

