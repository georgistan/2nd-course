import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinder;
import bg.sofia.uni.fmi.mjt.goodreads.finder.MatchOption;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommender;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite.CompositeSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (
            FileReader reader = new FileReader("goodreads_data.csv");
            FileReader fr = new FileReader("stopwords.txt");
        ) {
            TextTokenizer tokenizer = new TextTokenizer(fr);
            Set<Book> books = BookLoader.load(reader);
//            books.stream().limit(5).forEach(System.out::println); // Print loaded books
            for (Book book : books) {
                System.out.println(book);
            }
            System.out.println("-=====================");
//
            BookFinder bookFinder = new BookFinder(books, tokenizer);
//
//            System.out.println("all genres: " + bookFinder.allGenres());
//
//            Set<String> stringSet = new HashSet<>();
//            stringSet.add("Young Adult");
//            stringSet.add("Audiobook");
//            System.out.println(stringSet);
//
//            bookFinder.searchByAuthor("Stephen King").forEach(System.out::println);

//            Set<String> stringSet = new HashSet<>();
//            stringSet.add("military");
//            stringSet.add("plan");
//            System.out.println(stringSet);

//            bookFinder.searchByKeywords(stringSet, MatchOption.MATCH_ALL).forEach(System.out::println);

            List<Book> book1 = bookFinder.searchByAuthor("J.D. Salinger");
//            System.out.println(book1);
//            List<Book> book2 = bookFinder.searchByAuthor("J.R.R. Tolkien");
//            System.out.println(book2);
//
//            GenresOverlapSimilarityCalculator genreCalculator = new GenresOverlapSimilarityCalculator();
//            System.out.println("genre: " + genreCalculator.calculateSimilarity(book1.getFirst(), book2.getFirst()));
//
//            TFIDFSimilarityCalculator tfidfCalculator = new TFIDFSimilarityCalculator(books, tokenizer);
//            System.out.println("tf-idf: " + tfidfCalculator.calculateSimilarity(book1.getFirst(), book2.getFirst()));

//            Book book1 = books.stream().findFirst().get();
//            books.remove(book1);
//            System.out.println(book1);
//            Book book2 = books.stream().findFirst().get();
//            books.remove(book2);
//            System.out.println(book2);

//            System.out.println(calculator.calculateSimilarity(book1, book2));

//            Map<SimilarityCalculator, Double> similarityCalculatorMap = new HashMap<>();
//            similarityCalculatorMap.put(tfidfCalculator, 1.0);
//            similarityCalculatorMap.put(genreCalculator, 1.0);
//
//            CompositeSimilarityCalculator compositeCalculator = new CompositeSimilarityCalculator(similarityCalculatorMap);
//            System.out.println("composite: " + compositeCalculator.calculateSimilarity(book1.getFirst(), book2.getFirst()));

            TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(
                    books.stream().limit(200).collect(Collectors.toSet()), tokenizer
            );

            BookRecommender bookRecommender = new BookRecommender(books, calculator);
            SortedMap<Book, Double> recommendedBooks = bookRecommender.recommendBooks(book1.getFirst(), 20);

            for (Map.Entry<Book, Double> entry : recommendedBooks.entrySet()) {
                System.out.println(entry.getKey().ID() + " " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}