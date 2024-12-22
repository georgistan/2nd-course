package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextTokenizer {
    private static final String SPACE = " ";
    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines().collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {
        input = input
            .replaceAll("\\p{Punct}", "")
            .replaceAll("\\s+", SPACE)
            .trim();

        return Stream.of(input.split(SPACE))
            .filter(word -> !stopwords.contains(word))
            .toList();
    }

    public Set<String> stopwords() {
        return stopwords;
    }
}