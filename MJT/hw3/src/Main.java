import bg.sofia.uni.fmi.mjt.newsfeed.NewsFeed;
import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NewsArticleRequest newsArticleRequest = NewsArticleRequest.builder(List.of("trump"))
            .pageSize(20)
            .page(4)
            .build();

        System.out.println(newsArticleRequest);

        NewsFeed feed = new NewsFeed();
        feed.searchNews(newsArticleRequest);
        System.out.println("pages = " + newsArticleRequest.getPage());
        System.out.println("pageSize = " + newsArticleRequest.getPageSize());
    }
}