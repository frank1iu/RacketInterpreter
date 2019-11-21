package racket;

public abstract class AbstractRacketError extends RuntimeException {
    protected AbstractRacketError(String message) {
        super(message);
    }

    public abstract String toString();
}
