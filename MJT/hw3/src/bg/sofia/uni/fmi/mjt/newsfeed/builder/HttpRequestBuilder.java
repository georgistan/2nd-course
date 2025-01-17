package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import bg.sofia.uni.fmi.mjt.newsfeed.exception.NoKeywordException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;

public class HttpRequestBuilder {

    private static final String API_KEY = "aad42aefc56249daa8f4b7db96cf6697";
    private static final String SCHEME = "https";
    private static final String HOST = "newsapi.org";
    private static final String PATH = "/v2/top-headlines";

    public static HttpRequest buildRequest(List<String> keywords, String category, String country) {
        try {
            String query = buildQuery(keywords, category, country);

            URI uri = new URI(SCHEME, HOST, PATH, query, null);

            System.out.println(uri);

            return HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();
        } catch (NoKeywordException | URISyntaxException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private static String buildQuery(List<String> keywords, String category, String country)
        throws NoKeywordException {
        if (keywords == null || keywords.isEmpty()) {
            throw new NoKeywordException("Cannot build query without keywords");
        }

        StringBuilder query = new StringBuilder();
        query.append("q=");

        for (int i = 0; i < keywords.size(); i++) {
            query.append(keywords.get(i));

            if (i != keywords.size() - 1) {
                query.append(" ");
            }
        }

        if (category != null) {
            query.append("&category=").append(category);
        }

        if (country != null) {
            query.append("&country=").append(country);
        }

        query.append("&apiKey=").append(API_KEY);

        return query.toString();
    }
}
