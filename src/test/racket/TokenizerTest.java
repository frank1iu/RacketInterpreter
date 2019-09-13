package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TokenizerTest {
    private final String[] programs = {"(and true true)", "(and (or true false) true)"};
    @Test
    public void testSplit() {
        final Tokenizer t1 = new Tokenizer(programs[0]).split();
        Assertions.assertEquals(t1.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "true", "true", ")")));
        final Tokenizer t2 = new Tokenizer(programs[1]).split();
        Assertions.assertEquals(t2.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "(", "or", "true", "false", ")", "true", ")")));
    }
    @Test
    public void testTokenize() {
        final Tokenizer t1 = new Tokenizer(programs[1]).split().tokenize();
    }
}
