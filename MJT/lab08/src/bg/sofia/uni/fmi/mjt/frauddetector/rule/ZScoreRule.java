package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class ZScoreRule implements Rule {
    private double zScoreThreshold;
    private double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        this.zScoreThreshold = zScoreThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions cannot be null");
        }

        double mean = transactions.stream()
            .map(Transaction::transactionAmount)
            .reduce(
                0.0,
                Double::sum
            ) / transactions.size();

        double variance = transactions.stream()
            .map(Transaction::transactionAmount)
            .map(t -> (t - mean) * (t - mean))
            .reduce(
                0.0,
                Double::sum
            ) / transactions.size();

        double standardDeviation = Math.sqrt(variance);

        return transactions.stream()
            .map(Transaction::transactionAmount)
            .map(t -> (t - mean) / standardDeviation)
            .anyMatch(t -> t >= zScoreThreshold);
    }

    @Override
    public double weight() {
        return weight;
    }
}