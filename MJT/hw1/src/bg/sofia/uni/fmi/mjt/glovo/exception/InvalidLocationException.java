package bg.sofia.uni.fmi.mjt.glovo.exception;

public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException() {
    }

    public InvalidLocationException(String message) {
        super(message);
    }
}
