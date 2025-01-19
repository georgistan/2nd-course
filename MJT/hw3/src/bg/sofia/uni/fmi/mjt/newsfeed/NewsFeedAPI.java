package bg.sofia.uni.fmi.mjt.newsfeed;

import java.util.List;

public interface NewsFeedAPI {

    void searchNews(List<String> keywords, String category, String country);

}
