package racket;

import java.util.ArrayList;

public class Interpreter {
    public Interpreter() {

    }
    public Token interpretOne(ArrayList<Token> program) {
        final Token op = program.remove(0);
        final Token[] args = new Token[program.size()];
        for (int i = 0; i < program.size(); i++) {
            args[i] = program.get(i);
        }
        return this.eval(op, args);
    }
    public Token eval(Token operation, Token[] args) {
        if (operation.getType() != Type.IDENTIFIER) {
            // ...
        }
        switch (operation.getValue().toString()) {
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
    private Token and(Token[] args) {
        for (Token t : args) {
            if (!(Boolean) t.getValue()) {
                return new Token("false");
            }
        }
        return new Token("true");
    }
    private Token not(Token[] args) {
        // check length
        return new Token(!(Boolean) args[0].getValue());
    }
    private Token add(Token[] args) {
        int result = 0;
        for (Token t : args) {
            result += (Integer) t.getValue();
        }
        return new Token(result);
    }
}
