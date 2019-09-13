package racket;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    private String program;
    private ArrayList<String> split;
    private ArrayList<Thing> tokens;
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
        this.tokens = this.tokenizeRecursive(this.split, new ArrayList<Thing>());
        return this;
    }
    private ArrayList<Thing> tokenizeRecursive(ArrayList<String> program, ArrayList<Thing> list) {
        if (program.size() == 0) {
            return list;
        }
        final String first = program.remove(0);
        switch (first) {
            case "(":
                if (list.size() == 0) {
                    list.addAll(tokenizeRecursive(program, new ArrayList<Thing>()));
                } else {
                    list.get(0).addChildren(tokenizeRecursive(program, new ArrayList<Thing>()));
                }
                return tokenizeRecursive(program, list);
            case ")":
                return list;
            default:
                if (list.size() == 0) {
                    list.add(new Thing(first, new ArrayList<Thing>()));
                } else {
                    list.get(0).addChild(new Thing(first, new ArrayList<Thing>()));
                }
                return tokenizeRecursive(program, list);
        }
    }

    public ArrayList<String> getSplit() {
        return split;
    }

    public ArrayList<Thing> getTokens() {
        return tokens;
    }
}
