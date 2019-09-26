package racket;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    private String program;
    private ArrayList<String> split;
    private Thing thing;

    public Tokenizer(String program) {
        this.program = program;
    }

    // MODIFIES: this
    // EFFECTS: splits this.program and places into this.split
    public Tokenizer split() {
        this.split = new ArrayList<String>(Arrays.asList(this.program.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim()
                .split("\\s+")));
        return this;
    }

    // REQUIRES: this.split() has been called
    // MODIFIES: this
    // EFFECTS: construct a racket.Thing from this.split
    public Tokenizer tokenize() {
        this.thing = this.tokenizeRecursive(this.split, new ArrayList<Thing>()).get(0);
        return this;
    }

    // EFFECTS: construct a racket.Thing from parameter program
    private ArrayList<Thing> tokenizeRecursive(ArrayList<String> program, ArrayList<Thing> list) {
        if (program.size() == 0) {
            return list;
        }
        final String first = program.remove(0);
        switch (first) {
            case "(":
                branchOne(program, list);
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

    private void branchOne(ArrayList<String> program, ArrayList<Thing> list) {
        if (list.size() == 0) {
            list.addAll(tokenizeRecursive(program, new ArrayList<Thing>()));
        } else {
            list.get(0).addChildren(tokenizeRecursive(program, new ArrayList<Thing>()));
        }
    }

    public ArrayList<String> getSplit() {
        return split;
    }

    public Thing getThing() {
        return thing;
    }
}
