package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {
    private static final double WEIGHT_SUM_THRESHOLD = 1.0;

    private List<Transaction> transactions;
    private List<Rule> rules;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }

        BufferedReader bufferedReader = new BufferedReader(reader);
        transactions = bufferedReader.lines()
            .skip(1)
            .map(Transaction::of)
            .toList();

        setRules(rules);
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return transactions.stream()
            .map(Transaction::accountID)
            .distinct()
            .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        Map<Channel, List<Transaction>> map = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::channel));

        return map.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }

    @Override
    public double amountSpentByUser(String accountID) {
        validateAccountId(accountID);

        return transactions.stream()
            .filter(t -> t.accountID().equals(accountID))
            .reduce(
                0.0,
                (res, transaction) -> res + transaction.transactionAmount(),
                Double::sum
            );
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        validateAccountId(accountId);

        return transactions.stream()
            .filter(t -> t.accountID().equals(accountId))
            .toList();
    }

    @Override
    public double accountRating(String accountId) {
        validateAccountId(accountId);

        return rules.stream()
            .filter(rule -> rule.applicable(allTransactionsByUser(accountId)))
            .reduce(
                0.0,
                (res, el) -> res + el.weight(),
                Double::sum
            );
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        return allAccountIDs().stream()
            .collect(
                Collectors.toMap(
                    (accountID) -> accountID,
                    this::accountRating,
                    (e1, _) -> e1,
                    TreeMap::new
                )
            )
            .descendingMap();
    }

    private void setRules(List<Rule> rules) {
        double sumWeight = rules.stream()
            .map(Rule::weight)
            .reduce(
                0.0d,
                Double::sum,
                Double::sum
            );

        if (Double.compare(sumWeight, WEIGHT_SUM_THRESHOLD) != 0) {
            throw new IllegalArgumentException("Rules must sum to " + WEIGHT_SUM_THRESHOLD);
        }

        this.rules = rules;
    }

    private void validateAccountId(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }

        if (accountId.isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }
    }
}