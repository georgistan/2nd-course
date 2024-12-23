package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookFinder implements BookFinderAPI {
    private Set<Book> books;
    private TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    @Override
    public Set<Book> allBooks() {
        return books;
    }

    @Override
    public Set<String> allGenres() {
        return books.parallelStream()
            .flatMap(book -> book.genres().stream())
            .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        return books.parallelStream()
            .filter(book -> book.author().equals(authorName))
            .toList();
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException("Genres cannot be null");
        }

        return books.parallelStream()
            .filter(book -> matchesGenres(book, genres, option))
            .collect(Collectors.toList());
    }

    private boolean matchesGenres(Book book, Set<String> genres, MatchOption option) {
        switch (option) {
            case MATCH_ALL -> {
                return genres.parallelStream().allMatch(genre -> book.genres().contains(genre));
            }
            case MATCH_ANY -> {
                return genres.parallelStream().anyMatch(genre -> book.genres().contains(genre));
            }
        }

        return false;
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        return books.parallelStream()
            .filter(book -> matchesKeywords(book, keywords, option))
            .collect(Collectors.toList());
    }

    private boolean matchesKeywords(Book book, Set<String> keywords, MatchOption option) {
        String titleConcatDescription = book.title() + " " + book.description();
        Set<String> keywordsLowercase = keywords.parallelStream()
                                                .map(String::toLowerCase)
                                                .collect(Collectors.toSet());

        switch (option) {
            case MATCH_ALL -> {
                return keywordsLowercase.parallelStream().allMatch(titleConcatDescription::contains);
            }
            case MATCH_ANY -> {
                return keywordsLowercase.parallelStream().anyMatch(titleConcatDescription::equals);
            }
        }

        return false;
    }
}