package racket;

import java.util.ArrayList;
import java.util.Arrays;

public class RacketFunc extends Thing {
    private String[] params;

    private Thing body;

    public RacketFunc(String value, Thing body, String[] params) {
        super(value, null);
        this.params = params;
        this.body = body;
        super.setType(Type.FUNCTION);
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: executes the RacketFunc on args
    public Thing exec(Thing[] args, RacketContext parentContext) throws RacketSyntaxError {
        if (args.length != params.length) {
            final String errorMessage = this.getValue().toString() + ": expects " + this.params.length
                    + " arguments, but found " + args.length;
            throw new RacketSyntaxError(errorMessage,
                    new Thing(this.getValue().toString(), new ArrayList<Thing>(Arrays.asList(args))));
        } else {
            RacketContext context = new RacketContext(parentContext);
            for (int i = 0; i < this.params.length; i++) {
                context.put(this.params[i], args[i]);
            }
            final Interpreter interpreter = new Interpreter(context);
            return interpreter.eval(this.body);
        }
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns a string representation of this
    @Override
    public String toString() {
        String ret = "(" + this.getValue().toString();
        for (String param : params) {
            ret = ret + " " + param;
        }
        ret = ret + ")";
        return ret;
    }
}
