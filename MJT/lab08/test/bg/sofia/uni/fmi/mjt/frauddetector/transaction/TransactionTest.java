package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    void testIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Transaction.of("i,am,illegal"));
    }

    @Test
    void testHappyPath() {
        assertEquals(
            new Transaction(
                "TX000001",
                "AC00128",
                14.09,
                LocalDateTime.parse(
                    "2023-04-11 16:29:14",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ),
                "San Diego",
                Channel.ATM
            ),
            Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM")
        );
    }
}