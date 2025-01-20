package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticle;
import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticleResponse;
import bg.sofia.uni.fmi.mjt.newsfeed.request.HttpRequestBuilder;
import com.google.gson.Gson;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsFeed implements NewsFeedAPI {

    @Override
    public void searchNews(NewsArticleRequest newsArticleRequest) {
        // !!!!!!!!!!!!!!!!!!!!!! why cached threads? !!!!!!!!!!!!!!!!!!!!!!
        try (ExecutorService executor = Executors.newCachedThreadPool();
             HttpClient client = HttpClient.newBuilder().executor(executor).build()
        ) {
            HttpRequest request = HttpRequestBuilder.buildRequest(newsArticleRequest);

            CompletableFuture<HttpResponse<String>> future = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(x -> x);

            future.join();

            Gson gson = new Gson();
            NewsArticleResponse response = gson.fromJson(future.get().body(), NewsArticleResponse.class);
            System.out.println("responseSize = " + response.articles().size());
            System.out.println("totalResults = " + response.totalResults());
            for (NewsArticle article : response.articles()) {
                System.out.println(article);
                System.out.println();
            }

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}