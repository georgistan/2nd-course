package bg.sofia.uni.fmi.mjt.newsfeed.builder;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestBuilder {

    private static final String API_KEY = "aad42aefc56249daa8f4b7db96cf6697";
    private static final String SCHEME = "https://";
    private static final String HOST = "newsapi.org";
    private static final String PATH = "/v2/top-headlines/";

    public static HttpRequest buildRequest(String...args) {
//        URI uri = new URI("https", "/v2/top-headlines/everything", artist + "/" + song, null);
//        System.out.println(uri);

        StringBuilder uri = new StringBuilder(SCHEME + HOST + PATH + "?");

        for (int i = 0; i < args.length; i++) {
            appendArg(i, args[i], uri);
        }

        uri.append("apiKey=").append(API_KEY);

        System.out.println(uri);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri.toString())).build();

        return null;
    }

    private static void appendArg(int i, String arg, StringBuilder uri) {
        switch (i) {
            case 0: uri.append("q="); break;
            case 1: uri.append("from="); break;
            case 2: uri.append("sortBy="); break;
        }

        uri.append(arg).append("&");
    }
}
