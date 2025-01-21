package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import bg.sofia.uni.fmi.mjt.newsfeed.exception.NoKeywordException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class NewsArticleRequestTest {

    @Test
    public void testBuilderWithRequiredFields() {
        NewsArticleRequest request = NewsArticleRequest.builder(List.of("technology"))
            .category("general")
            .country("US")
            .pageSize(10)
            .page(1)
            .build();

        assertNotNull(request);
        assertEquals(List.of("technology"), request.getQ());
        assertEquals("general", request.getCategory());
        assertEquals("US", request.getCountry());
        assertEquals("10", request.getPageSize());
        assertEquals("1", request.getPage());
        assertNull(request.getSources());
        assertNull(request.getLanguage());
    }

    @Test
    public void testBuilderWithAllFields() {
        NewsArticleRequest request = NewsArticleRequest.builder(List.of("science", "health"))
            .category("science")
            .sources("bbc,cnn")
            .country("UK")
            .language("en")
            .pageSize(20)
            .page(2)
            .build();

        assertNotNull(request);
        assertEquals(Arrays.asList("science", "health"), request.getQ());
        assertEquals("science", request.getCategory());
        assertEquals("bbc,cnn", request.getSources());
        assertEquals("UK", request.getCountry());
        assertEquals("en", request.getLanguage());
        assertEquals("20", request.getPageSize());
        assertEquals("2", request.getPage());
    }

    @Test
    public void testBuilderWithNullKeywords() {
        assertThrows(NoKeywordException.class, () -> NewsArticleRequest.builder(null).build());
    }

    @Test
    public void testBuilderWithEmptyKeywords() {
        assertThrows(NoKeywordException.class, () -> NewsArticleRequest.builder(List.of()).build());
    }

    @Test
    public void testBuilderSetQToNull() {
        NewsArticleRequest.Builder builder = NewsArticleRequest.builder(List.of("trump"));

        assertThrows(NoKeywordException.class, () -> builder.q(null));
    }

    @Test
    public void testBuilderSetQToEmptyList() {
        NewsArticleRequest.Builder builder = NewsArticleRequest.builder(List.of("trump"));

        assertThrows(NoKeywordException.class, () -> builder.q(List.of()));
    }

    @Test
    public void testBuilderSetPageToZeroOrLower() {
        NewsArticleRequest.Builder builder = NewsArticleRequest.builder(List.of("trump"));

        assertThrows(IllegalArgumentException.class, () -> builder.page(-1));
    }

    @Test
    public void testBuilderSetPageSizeToZeroOrLower() {
        NewsArticleRequest.Builder builder = NewsArticleRequest.builder(List.of("trump"));

        assertThrows(IllegalArgumentException.class, () -> builder.pageSize(-1));
    }

    @Test
    public void testBuilderSetPageSizeGreaterThan100() {
        NewsArticleRequest.Builder builder = NewsArticleRequest.builder(List.of("trump"));

        assertThrows(IllegalArgumentException.class, () -> builder.pageSize(101));
    }
}