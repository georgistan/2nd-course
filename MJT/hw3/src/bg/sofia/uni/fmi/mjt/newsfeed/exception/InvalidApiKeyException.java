package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException() {
    }

    public InvalidApiKeyException(String message) {
        super(message);
    }
}
