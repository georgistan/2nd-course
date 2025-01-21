package bg.sofia.uni.fmi.mjt.newsfeed.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.NewsFeed;
import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsFeedTest {

    private NewsFeed newsFeed;
    private HttpClient mockHttpClient;

    @BeforeEach
    public void setup() {
        newsFeed = new NewsFeed();
        mockHttpClient = HttpClient.newBuilder().build();
    }

    @Test
    public void testSearchNewsNullRequest() {
        assertThrows(NullPointerException.class, () -> newsFeed.searchNews(null, mockHttpClient));
    }

    @Test
    public void testHappyPath() {
        NewsArticleRequest newsArticleRequest = NewsArticleRequest.builder(List.of("trump"))
            .pageSize(10)
            .page(1)
            .build();

        assertEquals(
            newsFeed.searchNews(newsArticleRequest, mockHttpClient).size(),
            Integer.parseInt(newsArticleRequest.getPageSize())
        );
    }
}