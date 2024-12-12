package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.temporal.TemporalAmount;
import java.util.List;

public class FrequencyRule implements Rule {
    private int transactionCountThreshold;
    private TemporalAmount timeWindow;
    private double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        validateInput(transactionCountThreshold, timeWindow, weight);

        this.transactionCountThreshold = transactionCountThreshold;
        this.timeWindow = timeWindow;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions cannot be null");
        }

        return transactions.stream()
            .anyMatch(
                t -> transactions.stream()
                    .filter(other ->
                        !other.transactionDate().isBefore(t.transactionDate()) &&
                        !other.transactionDate().isAfter(t.transactionDate().plus(timeWindow))
                    )
                    .count() >= transactionCountThreshold
            );
    }

    @Override
    public double weight() {
        return weight;
    }

    private void validateInput(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        if (transactionCountThreshold < 1) {
            throw new IllegalArgumentException("Transaction count threshold is invalid: " + transactionCountThreshold);
        }

        if (timeWindow == null) {
            throw new IllegalArgumentException("Time window cannot be null");
        }

        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("Weight must be between 0 and 1 inclusive");
        }
    }
}