package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.StatusCodeException;
import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticle;

import java.net.http.HttpClient;
import java.util.List;

public interface NewsFeedAPI {

    /**
     * Searches for news that apply to the criteria pointed in the NewsArticleRequest object
     *
     * @param newsArticleRequest an instance of a Builder class that contains as fields
     * the parameters that are to be searched for in a query
     * @param client a defined HTTP client that will be used for to call an async
     * HTTP request to the news API
     * @throws StatusCodeException if the status of the request is not 200
    */
    List<NewsArticle> searchNews(NewsArticleRequest newsArticleRequest, HttpClient client) throws StatusCodeException;

}
