package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationsRuleTest {
    private List<Transaction> transactions;

    @BeforeEach
    void setup() {
        transactions = List.of(
            Transaction.of("TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM"),
            Transaction.of("TX000002,AC00128,14.09,2023-04-11 16:29:14,Madrid,ATM"),
            Transaction.of("TX000003,AC00128,14.09,2023-04-11 16:29:14,Sofia,ATM")
        );
    }

    @Test
    void testNullTransactions() {
        LocationsRule rule = new LocationsRule(2, 0.4);

        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null));
    }

    @Test
    void testNotApplicable() {
        LocationsRule rule = new LocationsRule(2, 0.4);

        assertTrue(rule.applicable(transactions));
    }

    @Test
    void testApplicable() {
        LocationsRule rule = new LocationsRule(4, 0.4);

        assertFalse(rule.applicable(transactions));
    }
}