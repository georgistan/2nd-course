package bg.sofia.uni.fmi.mjt.newsfeed.mapper;

import bg.sofia.uni.fmi.mjt.newsfeed.record.NewsArticle;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NewsMapper {
    public void mapToNews() {
        Type newsType = new TypeToken<List<NewsArticle>>() {
        }.getType();


    }
}
