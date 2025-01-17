package bg.sofia.uni.fmi.mjt.newsfeed.record;

public record News(
    NewsSource source,
    String author,
    String title,
    String description,
    String url,
    String urlToImage,
    String publishedAt,
    String content
) { }
