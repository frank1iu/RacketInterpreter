package racket;

public class RacketSyntaxError extends Exception {
    private Thing program;

    public RacketSyntaxError(String errorMessage, Thing program) {
        super(errorMessage);
        this.program = program;
    }
}
