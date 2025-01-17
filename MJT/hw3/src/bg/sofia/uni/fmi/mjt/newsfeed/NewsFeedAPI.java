package bg.sofia.uni.fmi.mjt.newsfeed;

import java.util.List;

public interface NewsFeedAPI {

    void newsFeed(List<String> keywords, String category, String country);

}
