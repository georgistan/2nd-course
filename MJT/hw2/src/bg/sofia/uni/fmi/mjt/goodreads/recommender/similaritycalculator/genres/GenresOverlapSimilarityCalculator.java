package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot calculate similarity between null objects");
        }

        return first.genres().parallelStream()
            .filter(genre -> second.genres().contains(genre))
            .toList()
            .size() / (double) Integer.min(first.genres().size(), second.genres().size());
    }

}