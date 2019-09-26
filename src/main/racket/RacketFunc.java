package racket;

public class RacketFunc extends Thing {
    private String[] params;

    private Thing body;

    public RacketFunc(String value, Thing body, String[] params) {
        super(value, null);
        if (params == null) {
            this.params = new String[0];
        } else {
            this.params = params;
        }
        this.body = body;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: executes the RacketFunc on args
    public Thing exec(Thing[] args, RacketContext parentContext) throws RacketSyntaxError {
        if (args.length != params.length) {
            return null;
            // error here
        } else {
            RacketContext context = new RacketContext(parentContext);
            for (int i = 0; i < this.params.length; i++) {
                context.put(this.params[i], args[i]);
            }
            for (Thing arg : args) {
                replaceParams(arg, context);
            }
            final Interpreter interpreter = new Interpreter(context);
            return interpreter.eval(this.body);
        }
    }

    private void replaceParams(Thing thing, RacketContext context) {
        if (thing.getType() == Type.IDENTIFIER && context.containsKey(thing.getValue().toString())) {
            thing.setValue(context.get(thing.getValue().toString()).getValue().toString());
        }
        for (Thing child : thing.getChildren()) {
            replaceParams(child, context);
        }
    }
}
