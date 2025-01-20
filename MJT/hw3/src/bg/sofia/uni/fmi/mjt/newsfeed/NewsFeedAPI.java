package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.builder.NewsArticleRequest;

public interface NewsFeedAPI {

    /*
        message kato na 100yo
    */
    void searchNews(NewsArticleRequest newsArticleRequest);

}
