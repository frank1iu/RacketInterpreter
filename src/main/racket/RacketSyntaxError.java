package racket;

public class RacketSyntaxError extends RacketError {

    private Thing program;

    public RacketSyntaxError(String errorMessage, Thing program) {
        super(errorMessage);
        this.program = program;
    }

    public RacketSyntaxError(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Syntax Error: " + this.getMessage();
    }
}
