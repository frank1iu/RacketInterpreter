package racket;

public class RacketAssertionError extends RacketError {

    public RacketAssertionError(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Assertion Error: " + this.getMessage();
    }
}
