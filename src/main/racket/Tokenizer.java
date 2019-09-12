package racket;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    private String program;
    private ArrayList<String> split;
    private ArrayList<Object> tokens;
    public Tokenizer(String program) {
        this.program = program;
    }
    public Tokenizer split() {
        this.split = new ArrayList<String>(Arrays.asList(this.program.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim()
                .split("\\s+")));
        return this;
    }
    public Tokenizer tokenize() {
        this.tokens = this.tokenizeRecursive(this.split, new ArrayList<Object>());
        return this;
    }
    private ArrayList<Object> tokenizeRecursive(ArrayList<String> program, ArrayList<Object> list) {
        if (program.size() == 0) {
            return list;
        }
        final String first = program.remove(0);
        switch (first) {
            case "(":
                list.add(tokenizeRecursive(program, new ArrayList<Object>()));
                return tokenizeRecursive(program, list);
            case ")":
                return list;
            default:
                list.add(new Token(first));
                return tokenizeRecursive(program, list);
        }
    }

    public ArrayList<String> getSplit() {
        return split;
    }

    public ArrayList<Object> getTokens() {
        return tokens;
    }
}
