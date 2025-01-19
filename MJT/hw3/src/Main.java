import bg.sofia.uni.fmi.mjt.newsfeed.builder.HttpRequestBuilder;

import java.net.http.HttpRequest;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        HttpRequestBuilder.buildRequest(List.of("Trump", "Elon"), "popularity", "de");
//        HttpRequestBuilder.buildRequest(null, null, "us");
//        HttpRequestBuilder.buildRequest(null, "business", "de");
        HttpRequest request = HttpRequestBuilder.buildRequest(List.of("trump"), null, null);
        System.out.println(request.uri().toString());
//        HttpRequestBuilder.buildRequest(null, "popularity", "de");
    }
}