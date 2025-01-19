package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.HttpRequestBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsFeed implements NewsFeedAPI {

    @Override
    public void searchNews(List<String> keywords, String category, String country) {

        // !!!!!!!!!!!!!!!!!!!!!! why cached threads? !!!!!!!!!!!!!!!!!!!!!!
        try (ExecutorService executor = Executors.newCachedThreadPool();
             HttpClient client = HttpClient.newBuilder().executor(executor).build()
        ) {
            HttpRequest request = HttpRequestBuilder.buildRequest(keywords, category, country);

            // CompletableFuture ??

            // return List<NewsPage> ???
        }
    }
}