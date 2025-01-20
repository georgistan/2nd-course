import bg.sofia.uni.fmi.mjt.newsfeed.NewsFeed;
import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NewsArticleRequest newsArticleRequest = NewsArticleRequest.builder(List.of("Trump"))
            .country("us")
            .build();

        NewsFeed feed = new NewsFeed();
        feed.searchNews(newsArticleRequest);
    }
}