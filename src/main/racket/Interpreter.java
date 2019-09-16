package racket;

import java.util.HashMap;

public class Interpreter {
    private HashMap<String, Thing> context;
    public Interpreter(HashMap<String, Thing> context) {
        if (context == null) {
            this.context = new HashMap<String, Thing>();
        } else {
            this.context = context;
        }
    }
    // MODIFIES: this
    // EFFECTS: evaluates program
    public Thing eval(Thing program) {
        if (program.getChildren().size() == 0) {
            if (program.getType() == Type.IDENTIFIER) {
                return context.get(program.getValue());
            } else {
                return program;
            }
        }
        final Thing[] args = program.getChildren().toArray(new Thing[program.getChildren().size()]);
        switch (program.getValue().toString()) {
            case "and":
                return this.and(args);
            case "not":
                return this.not(args);
            case "+":
                return this.add(args);
            case "-":
                return this.sub(args);
            case "if":
                return this.ifFunc(args);
            case "check-expect":
                return this.checkExpect(args);
            case "define":
                this.define(args);
                return new Thing("(void)", null);
            default:
                System.out.println("placeholder for syntax error");
                return null;
        }
    }
    // EFFECTS: produces a boolean AND on args
    private Thing and(Thing[] args) {
        for (Thing t : args) {
            Thing arg = eval(t);
            if (!(Boolean) arg.getValue()) {
                return new Thing("false", null);
            }
        }
        return new Thing("true", null);
    }
    // EFFECTS: produces a boolean NOT on args[0]
    private Thing not(Thing[] args) {
        // check length
        Thing arg = eval(args[0]);
        return new Thing(new Boolean(!(Boolean) arg.getValue()).toString(), null);
    }
    // EFFECTS: adds up the args
    private Thing add(Thing[] args) {
        int result = 0;
        for (Thing t : args) {
            Thing arg = eval(t);
            result += (Integer) arg.getValue();
        }
        return new Thing(new Integer(result).toString(), null);
    }
    // EFFECTS: subtracts args[1] from args[0]
    private Thing sub(Thing[] args) {
        return new Thing(new Integer((Integer) args[0].getValue() - (Integer) args[1].getValue()).toString()
                , null);
    }
    private Thing ifFunc(Thing[] args) {
        if ((Boolean) eval(args[0]).getValue()) {
            return eval(args[1]);
        } else {
            return eval(args[2]);
        }
    }
    private Thing checkExpect(Thing[] args) {
        final Thing[] result = {eval(args[0]), eval(args[1])};
        if (result[0].getValue().equals(result[1].getValue())) {
            return new Thing("(void)", null);
        } else {
            this.error("Actual value " + result[0] + " differs from " + result[1] + ", the expected value.");
            return new Thing("(void)", null);
        }
    }
    private void define(Thing[] args) {
        this.context.put((String) args[0].getValue(), args[1]);
    }
    private void error(String msg) {
        System.err.println(msg);
    }
}
