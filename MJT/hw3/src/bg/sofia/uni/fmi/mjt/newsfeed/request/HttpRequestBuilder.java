package bg.sofia.uni.fmi.mjt.newsfeed.request;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.NoKeywordException;
import resources.Config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;

public class HttpRequestBuilder {

    private static final String SCHEME = "https";
    private static final String HOST = "newsapi.org";
    private static final String PATH = "/v2/top-headlines";

    public static HttpRequest buildRequest(NewsArticleRequest newsArticleRequest) {
        try {
            String query = buildQuery(newsArticleRequest);

            URI uri = new URI(SCHEME, HOST, PATH, query, null);

            System.out.println(uri);

            return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        } catch (NoKeywordException | URISyntaxException | IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private static String buildQuery(NewsArticleRequest newsArticleRequest)
        throws NoKeywordException, IOException {
        List<String> keywords = newsArticleRequest.getQ();
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

        String category = newsArticleRequest.getCategory();
        if (category != null) {
            query.append("&category=").append(category);
        }

        String country = newsArticleRequest.getCountry();
        if (country != null) {
            query.append("&country=").append(country);
        }

        query.append("&apiKey=").append(Config.getApiKey());
        return query.toString();
    }
}