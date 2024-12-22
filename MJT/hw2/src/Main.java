import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinder;
import bg.sofia.uni.fmi.mjt.goodreads.finder.MatchOption;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (
            FileReader reader = new FileReader("goodreads_data.csv");
            FileReader fr = new FileReader("stopwords.txt");
        ) {
            TextTokenizer tokenizer = new TextTokenizer(fr);
            Set<Book> books = BookLoader.load(reader);
            books.stream().limit(5).forEach(System.out::println); // Print loaded books

            BookFinder bookFinder = new BookFinder(books.stream().limit(500).collect(Collectors.toSet()), tokenizer);

            System.out.println("all genres: " + bookFinder.allGenres());

//            Set<String> stringSet = new HashSet<>();
//            stringSet.add("Young Adult");
//            stringSet.add("Audiobook");
//            System.out.println(stringSet);

            bookFinder.searchByAuthor("Stephen King").forEach(System.out::println);

            Set<String> stringSet = new HashSet<>();
            stringSet.add("military");
            stringSet.add("plan");
            System.out.println(stringSet);

            bookFinder.searchByKeywords(stringSet, MatchOption.MATCH_ALL).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
