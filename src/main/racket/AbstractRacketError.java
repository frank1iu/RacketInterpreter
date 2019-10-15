package racket;

public abstract class AbstractRacketError extends RuntimeException {
    public AbstractRacketError(String message) {
        super(message);
    }

    public abstract String toString();
}
