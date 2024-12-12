package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.temporal.TemporalAmount;
import java.util.List;

public class FrequencyRule implements Rule {
    private int transactionCountThreshold;
    private TemporalAmount timeWindow;
    private double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
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
}