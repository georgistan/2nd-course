package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException() {
    }

    public InvalidOrderException(String message) {
        super(message);
    }
}
