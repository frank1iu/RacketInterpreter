package racket;

import java.util.ArrayList;
import java.util.Arrays;

public class StringSplitter {
    private String program;
    private ArrayList<String> split;

    public StringSplitter(String program) {
        this.program = program;
    }

    // MODIFIES: this
    // EFFECTS: splits this.program and places into this.split
    public ArrayList<String> split() {
        this.split = new ArrayList<String>(Arrays.asList(this.program.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim()
                .split("\\s+")));
        return split;
    }
}
