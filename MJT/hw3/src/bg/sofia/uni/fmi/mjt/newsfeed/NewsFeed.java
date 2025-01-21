package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.StatusCodeException;
import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticle;
import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticleResponse;
import bg.sofia.uni.fmi.mjt.newsfeed.request.HttpRequestBuilder;
import com.google.gson.Gson;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NewsFeed implements NewsFeedAPI {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_BAD_REQUEST = 400;
    private static final int STATUS_CODE_UNAUTHORIZED = 401;
    private static final int STATUS_CODE_TOO_MANY_REQUESTS = 429;
    private static final int STATUS_CODE_SERVER_ERROR = 500;

    @Override
    public List<NewsArticle> searchNews(NewsArticleRequest newsArticleRequest, HttpClient client) {
        try {
            HttpRequest request = HttpRequestBuilder.buildRequest(newsArticleRequest);

            CompletableFuture<HttpResponse<String>> future = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

            future.join();

            switch (future.get().statusCode()) {
                case STATUS_CODE_OK: {
                    Gson gson = new Gson();
                    NewsArticleResponse response = gson.fromJson(future.get().body(), NewsArticleResponse.class);

                    return response.articles();
                }
                case STATUS_CODE_BAD_REQUEST: throw new StatusCodeException("Status code 400, invalid parameter(s)");
                case STATUS_CODE_UNAUTHORIZED:
                    throw new StatusCodeException("Status code 401, Invalid or missing API key");
                case STATUS_CODE_TOO_MANY_REQUESTS: throw new StatusCodeException("Status code 429, too many requests");
                case STATUS_CODE_SERVER_ERROR: throw new StatusCodeException("Status code 500, server error");
            }
        } catch (ExecutionException | InterruptedException | StatusCodeException exception) {
            exception.printStackTrace();
        }

        return List.of();
    }

}