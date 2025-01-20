package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.request.HttpRequestBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsFeed implements NewsFeedAPI {

    @Override
    public void searchNews(NewsArticleRequest newsArticleRequest) {
        System.out.println(LocalDateTime.now());

        // !!!!!!!!!!!!!!!!!!!!!! why cached threads? !!!!!!!!!!!!!!!!!!!!!!
        try (ExecutorService executor = Executors.newCachedThreadPool();
             HttpClient client = HttpClient.newBuilder().executor(executor).build()
        ) {
            HttpRequest request = HttpRequestBuilder.buildRequest(newsArticleRequest);

            CompletableFuture<HttpResponse<String>> future = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(x -> x);

            future.thenAcceptAsync( x ->
                System.out.println(x.body())
            );

            future.join();
            // return List<NewsPage> ???

            System.out.println(future.isDone());
        }

        System.out.println(LocalDateTime.now());
    }
}