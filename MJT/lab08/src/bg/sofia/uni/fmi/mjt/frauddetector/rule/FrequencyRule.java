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
        return false;
    }

    @Override
    public double weight() {
        return 0;
    }
}
