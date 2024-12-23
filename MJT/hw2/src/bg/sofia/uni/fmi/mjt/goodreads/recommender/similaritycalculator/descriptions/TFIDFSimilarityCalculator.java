package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {
    private Set<Book> books;
    private TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot calculate similarity between null objects");
        }

        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        Map<String, Double> tfScores = computeTF(book);
        Map<String, Double> idfScores = computeIDF(book);

        return tfScores.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue() * idfScores.get(e.getKey())
                )
            );
    }

    public Map<String, Double> computeTF(Book book) {
        List<String> tokenizedDescription = tokenizer.tokenize(book.description());
        int wordCount = tokenizedDescription.size();

        return tokenizedDescription.parallelStream()
            .distinct()
            .collect(
                Collectors.toMap(
                    word -> word,
                    word -> ((double) Collections.frequency(tokenizedDescription, word) / wordCount)
                )
            );
    }

    public Map<String, Double> computeIDF(Book book) {
        List<String> tokenizedDescription = tokenizer.tokenize(book.description());

        Function<String, Long> getWordFrequencyInAllBooks =
            word -> books.parallelStream()
                                .filter(curr -> tokenizer.tokenize(curr.description()).contains(word))
                                .count();

        int booksCount = books.size();

        return tokenizedDescription.parallelStream()
            .distinct()
            .collect(
                Collectors.toMap(
                    word -> word,
                    word -> Math.log10((double) booksCount / getWordFrequencyInAllBooks.apply(word))
                )
            );
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}