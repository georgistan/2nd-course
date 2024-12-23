package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Map;
import java.util.function.Function;

public class CompositeSimilarityCalculator implements SimilarityCalculator {
    Map<SimilarityCalculator, Double> similarityCalculatorMap;

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {
        this.similarityCalculatorMap = similarityCalculatorMap;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot calculate similarity between null objects");
        }

        Function<Map.Entry<SimilarityCalculator, Double>, Double> getCalculatorProductOfSimilarityAndWeight =
            entry -> entry.getKey().calculateSimilarity(first, second) * entry.getValue();

        return similarityCalculatorMap.entrySet().parallelStream()
            .map(getCalculatorProductOfSimilarityAndWeight)
            .reduce(
                0.0,
                Double::sum
            );
    }

}