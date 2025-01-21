package bg.sofia.uni.fmi.mjt.newsfeed.request;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.NoKeywordException;
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

    private static final int TOTAL_RESPONSES_PER_QUERY = 100;

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
        StringBuilder query = new StringBuilder();

        putKeywords(query, newsArticleRequest.getQ());

        putCategoryAndCountry(query, newsArticleRequest.getCategory(), newsArticleRequest.getCountry());

        handlePagination(query, newsArticleRequest.getPage(), newsArticleRequest.getPageSize());

        query.append("&apiKey=").append(Config.getApiKey());

        System.out.println(query);

        return query.toString();
    }

    private static void putKeywords(StringBuilder query, List<String> keywords) throws NoKeywordException {
        if (keywords == null || keywords.isEmpty()) {
            throw new NoKeywordException("Cannot build query without keywords");
        }

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
        if (page != null) {
            query.append("&page=").append(page);
        }

        if (pageSize != null) {
            query.append("&pageSize=").append(pageSize);
        }
    }
}