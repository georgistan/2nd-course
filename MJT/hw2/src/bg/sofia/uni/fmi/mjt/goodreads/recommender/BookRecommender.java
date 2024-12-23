package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookRecommender implements BookRecommenderAPI {
    private Set<Book> initialBooks;
    private SimilarityCalculator similarityCalculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.similarityCalculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin book cannot be null");
        }

        if (maxN <= 0) {
            throw new IllegalArgumentException("Maximum number of recommendations must be greater than zero");
        }

        return null;
//        return initialBooks.stream()
//            .collect(
//                Collectors.toMap(
//                    (book) -> book,
//                    (value) -> similarityCalculator.calculateSimilarity(value, origin),
//                    TreeMap::new
//                )
//            )
//            .get();
    }
}