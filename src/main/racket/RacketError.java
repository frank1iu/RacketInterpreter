package racket;

public abstract class RacketError extends Exception {
    public RacketError(String message) {
        super(message);
    }

    public abstract String toString();
}
