package bg.sofia.uni.fmi.mjt.newsfeed.request;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.InvalidApiKeyException;
import bg.sofia.uni.fmi.mjt.newsfeed.resources.Config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;

public class HttpRequestBuilder {

    private static final String SCHEME = "https";
    private static final String HOST = "newsapi.org";
    private static final String PATH = "/v2/top-headlines";

    private static final int MAX_RESPONSE_SIZE = 100;

    public static HttpRequest buildRequest(NewsArticleRequest newsArticleRequest) {
        try {
            String query = buildQuery(newsArticleRequest);

            URI uri = new URI(SCHEME, HOST, PATH, query, null);

            return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private static String buildQuery(NewsArticleRequest newsArticleRequest)
        throws IOException {
        if (newsArticleRequest == null) {
            throw new NullPointerException("newsArticleRequest parameter is null");
        }

        StringBuilder query = new StringBuilder();

        putKeywords(query, newsArticleRequest.getQ());

        putCategoryAndCountry(query, newsArticleRequest.getCategory(), newsArticleRequest.getCountry());

        handlePagination(query, newsArticleRequest.getPage(), newsArticleRequest.getPageSize());

        putApiKey(query);

        return query.toString();
    }

    private static void putKeywords(StringBuilder query, List<String> keywords) {
        query.append("q=");

        for (int i = 0; i < keywords.size(); i++) {
            query.append(keywords.get(i));

            if (i != keywords.size() - 1) {
                query.append(" ");
            }
        }
    }

    private static void putCategoryAndCountry(StringBuilder query, String category, String country) {
        if (category != null) {
            query.append("&category=").append(category);
        }

        if (country != null) {
            query.append("&country=").append(country);
        }
    }

    private static void handlePagination(StringBuilder query, String page, String pageSize) {
        if (page == null && pageSize == null) {
            return;
        }

        if (page != null &&
            pageSize != null &&
            Integer.parseInt(page) * Integer.parseInt(pageSize) > MAX_RESPONSE_SIZE
        ) {
            throw new IllegalArgumentException("Invalid page size, cannot be reached");
        }

        if (page != null) {
            query.append("&page=").append(page);
        }

        if (pageSize != null) {
            query.append("&pageSize=").append(pageSize);
        }
    }

    private static void putApiKey(StringBuilder query) {
        try {
            String apiKey = Config.getApiKey();

            if (apiKey.isEmpty()) {
                throw new InvalidApiKeyException("API key is empty");
            }

            query.append("&apiKey=").append(apiKey);
        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}