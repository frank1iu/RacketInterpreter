package racket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Interpreter {
    private RacketContext context;

    public Interpreter(RacketContext context) {
        if (context == null) {
            this.context = new RacketContext(null);
        } else {
            this.context = context;
        }
        try {
            this.loadFile(Paths.get(System.getProperty("user.dir") + "/lib/init.rkt"));
        } catch (RacketSyntaxError racketSyntaxError) {
            racketSyntaxError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: reads and evaluates file

    public void loadFile(Path path) throws IOException, RacketSyntaxError {
        final String program = new String(Files.readAllBytes(path));
        final ArrayList<String> expressions = new ArrayList<String>(Arrays.asList(program.split("\n\n")));
        // DrRacket inserts three lines at the top, so we start at 1
        for (int i = 1; i < expressions.size(); i++) {
            this.eval(new Tokenizer(expressions.get(i)).split().tokenize().getThing());
        }
    }

    // MODIFIES: this
    // EFFECTS: evaluates program

    public Thing eval(Thing program) throws RacketSyntaxError {
        if (program.getChildren().size() == 0) {
            if (program.getType() == Type.IDENTIFIER) {
                Thing thing = context.get(program.getValue().toString());
                if (thing.getType() == Type.IDENTIFIER) {
                    return eval(thing);
                }
                if (thing.getChildren().size() == 0) {
                    return thing;
                } else {
                    return eval(thing);
                }
            } else {
                return program;
            }
        }
        final Thing[] args = program.getChildren().toArray(new Thing[program.getChildren().size()]);
        final String op = program.getValue().toString();
        return switchStatement(op, args, program);
    }

    private Thing switchStatement(String op, Thing[] args, Thing program) throws RacketSyntaxError {
        switch (op) {
            case "and":
                return this.and(args);
            case "not":
                return this.not(args);
            case "or":
                return this.or(args);
            case "+":
                return this.add(args);
            case "-":
                return this.sub(args);
            case ">":
                return this.greaterThan(args);
            case "<":
                return this.lessThan(args);
            default:
                return switchStatement2(op, args, program);
        }
    }

    private Thing switchStatement2(String op, Thing[] args, Thing program) throws RacketSyntaxError {
        switch (op) {
            case "if":
                return this.ifFunc(args);
            case "check-expect":
                return this.checkExpect(args);
            case "=":
                return this.equiv(args);
            case "define":
                this.define(args);
                return new Thing("(void)", null);
            default:
                if (this.context.containsKey(op)) {
                    return ((RacketFunc) this.context.get(op)).exec(args, context);
                } else {
                    throw new RacketSyntaxError(op + ": this function is not defined", program);
                }
        }
    }

    // EFFECTS: produces a boolean AND on args
    private Thing and(Thing[] args) throws RacketSyntaxError {
        for (Thing t : args) {
            Thing arg = eval(t);
            if (!(Boolean) arg.getValue()) {
                return new Thing("false", null);
            }
        }
        return new Thing("true", null);
    }

    // EFFECTS: produces a boolean NOT on args[0]
    private Thing not(Thing[] args) throws RacketSyntaxError {
        // check length
        Thing arg = eval(args[0]);
        return new Thing(Boolean.toString(!(Boolean) arg.getValue()), null);
    }

    // EFFECTS: produces a boolean OR on args[0]
    private Thing or(Thing[] args) throws RacketSyntaxError {
        for (Thing t : args) {
            Thing arg = eval(t);
            if ((Boolean) arg.getValue()) {
                return new Thing("true", null);
            }
        }
        return new Thing("false", null);
    }

    // EFFECTS: adds up the args
    private Thing add(Thing[] args) throws RacketSyntaxError {
        int result = 0;
        for (Thing t : args) {
            Thing arg = eval(t);
            result += (Integer) arg.getValue();
        }
        return new Thing(Integer.toString(result), null);
    }

    // EFFECTS: subtracts args[1] from args[0]
    private Thing sub(Thing[] args) throws RacketSyntaxError {
        return new Thing(Integer.toString((Integer) eval(args[0]).getValue() - (Integer) eval(args[1])
                .getValue()), null);
    }

    private Thing ifFunc(Thing[] args) throws RacketSyntaxError {
        if ((Boolean) eval(args[0]).getValue()) {
            return eval(args[1]);
        } else {
            return eval(args[2]);
        }
    }

    private Thing checkExpect(Thing[] args) throws RacketSyntaxError {
        final Thing[] result = {eval(args[0]), eval(args[1])};
        final Thing ret = new Thing("(void)", null);
        if (result[0].getValue().equals(result[1].getValue())) {
            return ret;
        } else {
            this.error("Actual value " + result[0] + " differs from " + result[1] + ", the expected value.");
            return ret;
        }
    }

    // MODIFIES: this
    // EFFECTS: defines a variable or function to store in context

    private void define(Thing[] args) throws RacketSyntaxError {
        if (args[0].getChildren().size() != 0) {
            this.defineFunction(args);
        } else {
            this.context.put((String) args[0].getValue(), eval(args[1]));
        }
    }

    // EFFECTS: return true if args[0] > args[1]

    private Thing greaterThan(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() > (Integer) eval(args[1]).getValue()),
                null);
    }

    // EFFECTS: return true if args[0] < args[1]

    private Thing lessThan(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() < (Integer) eval(args[1]).getValue()),
                null);
    }

    private Thing equiv(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() == (Integer) eval(args[1]).getValue()),
                null);
    }

    // MODIFIES: this
    private void defineFunction(Thing[] args) {
        final String name = args[0].getValue().toString();
        String[] params = new String[args[0].getChildren().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = args[0].getChildren().get(i).getValue().toString();
        }
        this.context.put(name, new RacketFunc(name, args[1], params));
    }

    private void error(String msg) {
        System.err.println(msg);
    }
}
