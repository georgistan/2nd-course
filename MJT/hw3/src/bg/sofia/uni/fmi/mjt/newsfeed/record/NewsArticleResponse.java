package bg.sofia.uni.fmi.mjt.newsfeed.record;

import java.util.List;

public record NewsArticleResponse(
    String status,
    int totalResults,
    List<NewsArticle> articles
) { }