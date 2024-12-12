package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZScoreRuleTest {
    private List<Transaction> transactions;
    private ZScoreRule rule;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        transactions.add(
            Transaction.of("TX000001,AC00128,10.00,2023-04-11 16:29:14,San Diego,ATM")
        );
        transactions.add(
            Transaction.of("TX000001,AC00128,05.00,2023-04-11 16:29:14,San Diego,ATM")
        );
        transactions.add(
            Transaction.of("TX000001,AC00128,10.00,2023-04-11 16:29:14,San Diego,ATM")
        );

        rule = new ZScoreRule(1.5,  0.4);
    }

    @Test
    void testNullTransactions() {
        assertThrows(IllegalArgumentException.class, () -> rule.applicable(null));
    }

    @Test
    void testNotApplicable() {
        assertFalse(rule.applicable(transactions));
    }

    @Test
    void testApplicable() {
        Transaction transaction = mock(Transaction.class);
        when(transaction.transactionAmount()).thenReturn(10000.0);

        transactions.add(transaction);

        assertTrue(rule.applicable(transactions));
    }
}