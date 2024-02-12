package org.example.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConfig {

    public static void setUpDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE INSTRUMENT_PRICE_MODIFIER (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "NAME VARCHAR(255)," +
                    "MULTIPLIER DECIMAL(10, 2))");

            stmt.execute("INSERT INTO INSTRUMENT_PRICE_MODIFIER (NAME, MULTIPLIER) VALUES ('INSTRUMENT1', 1.1)");
            stmt.execute("INSERT INTO INSTRUMENT_PRICE_MODIFIER (NAME, MULTIPLIER) VALUES ('INSTRUMENT2', 1.2)");
            stmt.execute("INSERT INTO INSTRUMENT_PRICE_MODIFIER (NAME, MULTIPLIER) VALUES ('INSTRUMENT3', 0.9)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

