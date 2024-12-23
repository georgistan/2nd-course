package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.Map;
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

        Map<Book, Double> map = initialBooks.stream()
            .collect(
                Collectors.toMap(
                    book -> book,
                    book -> similarityCalculator.calculateSimilarity(origin, book)
                )
            );

        Comparator<Book> bookComparator = (b1, b2) -> {
            int compareRes = map.get(b2).compareTo(map.get(b1));
            if (compareRes == 0) {
                return Double.compare(b2.rating(), b1.rating());
            }
            return compareRes;
        };

        TreeMap<Book, Double> sortedMap = new TreeMap<>(bookComparator);
        sortedMap.putAll(map);

        return sortedMap;
    }
}