package racket;

public class GenericRacketError extends AbstractRacketError {

    public GenericRacketError(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Error: " + this.getMessage();
    }
}
