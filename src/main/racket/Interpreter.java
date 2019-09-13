package racket;

import java.util.ArrayList;

public class Interpreter {
    public Interpreter() {

    }
    public Thing eval(Thing program) {
        final Thing[] args = (Thing[]) program.getChildren().toArray(new Thing[program.getChildren().size()]);
        switch (program.getValue().toString()) {
            case "and":
                return this.and(args);
            case "not":
                return this.not(args);
            case "+":
                return this.add(args);
            default:
                System.out.println("error here");
                return null;
        }
    }
    private Thing and(Thing[] args) {
        for (Thing t : args) {
            if (!(Boolean) t.getValue()) {
                return new Thing("false", null);
            }
        }
        return new Thing("true", null);
    }
    private Thing not(Thing[] args) {
        // check length
        return new Thing(!(Boolean) args[0].getValue(), null);
    }
    private Thing add(Thing[] args) {
        int result = 0;
        for (Thing t : args) {
            result += (Integer) t.getValue();
        }
        return new Thing(result, null);
    }
}
