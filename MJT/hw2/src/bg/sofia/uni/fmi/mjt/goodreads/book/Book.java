package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL
) {
    private static final int PARAMETERS_COUNT = 8;
    private static final String GENRE_ITEM_SEPARATOR = ",";

    private static final int DESCRIPTION_INDEX = 3;
    private static final int GENRES_INDEX = 4;
    private static final int RATING_INDEX = 5;
    private static final int RATING_COUNT_INDEX = 6;
    private static final int URL_INDEX = 7;

    public static Book of(String[] tokens) {
        if (tokens.length != PARAMETERS_COUNT) {
            throw new IllegalArgumentException("Invalid number of parameters");
        }

        return new Book(
            tokens[0],
            tokens[1],
            tokens[2],
            tokens[DESCRIPTION_INDEX],

            Arrays.stream(tokens[GENRES_INDEX]
                .substring(1, tokens[GENRES_INDEX].length() - 1)
                .replace("'", "")
                .split(GENRE_ITEM_SEPARATOR)
            )
            .map(String::trim)
            .toList(),

            Double.parseDouble(tokens[RATING_INDEX]),
            Integer.parseInt(tokens[RATING_COUNT_INDEX].replace(GENRE_ITEM_SEPARATOR, "")),
            tokens[URL_INDEX]
        );
    }
}