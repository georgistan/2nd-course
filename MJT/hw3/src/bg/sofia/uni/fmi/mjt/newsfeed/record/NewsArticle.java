package bg.sofia.uni.fmi.mjt.newsfeed.record;

public record NewsArticle(
    NewsSource source,
    String author,
    String title,
    String description,
    String url,
    String urlToImage,
    String publishedAt,
    String content
) {
    @Override
    public String toString() {
        return source + "\n" +
            "author = " + author + "\n" +
            "title = " + title + "\n" +
            "description = " + description + "\n" +
            "url = " + url + "\n" +
            "urlToImage = " + urlToImage + "\n" +
            "publishedAt = " + publishedAt + "\n" +
            "content = " + content;
    }
}
