package bg.sofia.uni.fmi.mjt.newsfeed.request;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestBuilderTest {

    private NewsArticleRequest newsArticleRequest;

    @BeforeEach
    public void setup() {
        newsArticleRequest = NewsArticleRequest.builder(List.of("technology", "AI"))
            .category("science")
            .country("us")
            .pageSize(10)
            .page(1)
            .build();
    }

    @Test
    @Order(1)
    public void testBuildURI() {
        HttpRequest request = HttpRequestBuilder.buildRequest(newsArticleRequest);

        assertNotNull(request);
        URI uri = request.uri();

        assertTrue(uri.toString().contains("q=technology%20AI"));
        assertTrue(uri.toString().contains("category=science"));
        assertTrue(uri.toString().contains("country=us"));
        assertTrue(uri.toString().contains("page=1"));
        assertTrue(uri.toString().contains("pageSize=10"));
    }

    @Test
    @Order(2)
    public void testBuildRequestWithNullRequest() {
        assertThrows(NullPointerException.class, () -> HttpRequestBuilder.buildRequest(null));
    }

    @Test
    public void testBuildRequestMissingParameters() {
        NewsArticleRequest requestWithoutCategoryAndCountry = NewsArticleRequest.builder(List.of("sports"))
            .build();

        HttpRequest request = HttpRequestBuilder.buildRequest(requestWithoutCategoryAndCountry);

        assertNotNull(request);

        String uri = request.uri().toString();

        assertFalse(uri.contains("category"));
        assertFalse(uri.contains("country"));
        assertFalse(uri.contains("page"));
        assertFalse(uri.contains("pageSize"));
    }

    @Test
    public void testBuildRequestContainsApiKey() {
        HttpRequest request = HttpRequestBuilder.buildRequest(newsArticleRequest);

        assertNotNull(request);

        Pattern pattern = Pattern.compile("apiKey=.*");
        Matcher matcher = pattern.matcher(request.uri().toString());

        assertTrue(matcher.find());
    }

    @Test
    public void testBuildRequestWithNullPageNullPageSize() {
        NewsArticleRequest requestWithoutCategoryAndCountry = NewsArticleRequest.builder(List.of("sports"))
            .build();

        HttpRequest request = HttpRequestBuilder.buildRequest(requestWithoutCategoryAndCountry);
        assertNotNull(request);

        String uri = request.uri().toString();

        assertFalse(uri.contains("page"));
        assertFalse(uri.contains("pageSize"));
    }

    @Test
    public void testBuildRequestWithInvalidPage() {
        NewsArticleRequest request = NewsArticleRequest.builder(List.of("tech"))
            .page(4)
            .pageSize(30)
            .build();

        assertThrows(IllegalArgumentException.class, () -> HttpRequestBuilder.buildRequest(request));
    }
}