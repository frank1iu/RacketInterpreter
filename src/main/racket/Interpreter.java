package racket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Interpreter implements FileReader, FileWriter {
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

    // REQUIRES: valid file path to a ASCII rkt file created by DrRacket
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

    // REQUIRES: valid racket program
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
            default:
                return switchStatement2(op, args, program);
        }
    }

    private Thing switchStatement2(String op, Thing[] args, Thing program) throws RacketSyntaxError {
        switch (op) {
            case "<":
                return this.lessThan(args);
            case "if":
                return this.ifFunc(args);
            case "check-expect":
                try {
                    return this.checkExpect(args);
                } catch (RacketAssertionError racketAssertionError) {
                    racketAssertionError.printStackTrace();
                    return null;
                }
            case "=":
                return this.equiv(args);
            default:
                return this.switchStatement3(op, args, program);
        }
    }

    private Thing switchStatement3(String op, Thing[] args, Thing program) throws RacketSyntaxError {
        switch (op) {
            case "*":
                return this.mult(args);
            case "pow":
                return this.pow(args);
            case "save!":
                return this.writeToFile(args);
            case "define":
                this.define(args);
                return new Thing("(void)", null);
            default:
                return switchStatementDefault(op, args, program);
        }
    }

    private Thing switchStatementDefault(String op, Thing[] args, Thing program) throws RacketSyntaxError {
        if (this.context.containsKey(op)) {
            Thing[] params = new Thing[args.length];
            for (int i = 0; i < params.length; i++) {
                params[i] = eval(args[i]);
            }
            return ((RacketFunc) this.context.get(op)).exec(params, context);
        } else {
            throw new RacketSyntaxError(op + ": this function is not defined", program);
        }
    }



    // REQUIRES: boolean args
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

    // REQUIRES: boolean arg
    // EFFECTS: produces a boolean NOT on args[0]
    private Thing not(Thing[] args) throws RacketSyntaxError {
        // check length
        Thing arg = eval(args[0]);
        return new Thing(Boolean.toString(!(Boolean) arg.getValue()), null);
    }

    // REQUIRES: boolean args
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

    // REQUIRES: integer args
    // EFFECTS: adds up the args
    private Thing add(Thing[] args) throws RacketSyntaxError {
        int result = 0;
        for (Thing t : args) {
            Thing arg = eval(t);
            result += (Integer) arg.getValue();
        }
        return new Thing(Integer.toString(result), null);
    }

    // REQUIRES: integer args
    // EFFECTS: subtracts args[1] from args[0]
    private Thing sub(Thing[] args) throws RacketSyntaxError {
        return new Thing(Integer.toString((Integer) eval(args[0]).getValue() - (Integer) eval(args[1])
                .getValue()), null);
    }

    // REQUIRES: one boolean arg, two any args
    // EFFECTS: returns first arg if boolean arg is true and second arg otherwise
    private Thing ifFunc(Thing[] args) throws RacketSyntaxError {
        if ((Boolean) eval(args[0]).getValue()) {
            return eval(args[1]);
        } else {
            return eval(args[2]);
        }
    }

    // REQUIRES: two args
    // EFFECTS: returns void but prints error if two args aren't equal
    private Thing checkExpect(Thing[] args) throws RacketSyntaxError, RacketAssertionError {
        final Thing[] result = {eval(args[0]), eval(args[1])};
        final Thing ret = new Thing("(void)", null);
        if (result[0].getValue().equals(result[1].getValue())) {
            return ret;
        } else {
            throw new RacketAssertionError("Actual value "
                    + result[0] + " differs from " + result[1] + ", the expected value.", result[0], result[1]);
        }
    }

    // REQUIRES: an identifier arg and a value arg
    // MODIFIES: this
    // EFFECTS: defines a variable to store in context

    private void define(Thing[] args) throws RacketSyntaxError {
        if (args[0].getChildren().size() != 0) {
            this.defineFunction(args);
        } else {
            this.context.put((String) args[0].getValue(), eval(args[1]));
        }
    }

    // REQUIRES: two integer args
    // EFFECTS: return true if args[0] > args[1]

    private Thing greaterThan(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() > (Integer) eval(args[1]).getValue()),
                null);
    }

    // REQUIRES: two integer args
    // EFFECTS: return true if args[0] < args[1]

    private Thing lessThan(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() < (Integer) eval(args[1]).getValue()),
                null);
    }

    // REQUIRES: two integer args
    // EFFECTS: return true if args[0] = args[1]
    private Thing equiv(Thing[] args) throws RacketSyntaxError {
        return new Thing(Boolean.toString((Integer) eval(args[0]).getValue() == (Integer) eval(args[1]).getValue()),
                null);
    }

    // REQUIRES: a well-formatted function definition
    // MODIFIES: this
    // EFFECTS: defines a function to store in context
    private void defineFunction(Thing[] args) {
        final String name = args[0].getValue().toString();
        String[] params = new String[args[0].getChildren().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = args[0].getChildren().get(i).getValue().toString();
        }
        this.context.put(name, new RacketFunc(name, args[1], params));
    }

    // REQUIRES: the content to write
    // MODIFIES: none
    // EFFECTS: writes the content to result file
    //          and return true if successful, false otherwise
    private Thing writeToFile(Thing[] args) throws RacketSyntaxError {
        final Path path = Paths.get(System.getProperty("user.dir") + "/lib/result");
        try {
            this.writeFile(path, eval(args[0]).getValue().toString());
            return new Thing("true", null);
        } catch (IOException e) {
            return new Thing("false", null);
        }
    }

    // REQUIRES: two integer args
    // MODIFIES: none
    // EFFECTS: returns the product of the args
    private Thing mult(Thing[] args) throws RacketSyntaxError {
        return new Thing(Integer.toString((Integer) eval(args[0]).getValue() * (Integer) eval(args[1])
                .getValue()), null);
    }

    // REQUIRES: two integer args
    // MODIFIES: none
    // EFFECTS: returns the args[0]^args[1]
    private Thing pow(Thing[] args) throws RacketSyntaxError {
        final Integer arg0 = (Integer) eval(args[0]).getValue();
        final Integer arg1 = (Integer) eval(args[1]).getValue();
        return new Thing(Integer.toString((int) Math.pow(arg0, arg1)), null);
    }

    // REQUIRES: valid path and content
    // MODIFIES: none
    // EFFECTS: writes content to the specified file

    public void writeFile(Path path, String content) throws IOException {
        Files.write(path, Collections.singleton(content), StandardCharsets.UTF_8);
    }
}
