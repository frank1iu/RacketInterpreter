package racket;

public class RacketAssertionError extends Exception {
    private Thing actual;
    private Thing expected;

    public RacketAssertionError(String errorMessage, Thing expected, Thing actual) {
        super(errorMessage);
        this.expected = expected;
        this.actual = actual;
    }
}
