package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TokenizerTest {
    private final String[] programs = {"(and true true)", "(and (or true false) true)", "(+ 1 2)"};
    @Test
    public void testSplit() {
        final Tokenizer t1 = new Tokenizer(programs[0]).split();
        Assertions.assertEquals(t1.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "true", "true", ")")));
        final Tokenizer t2 = new Tokenizer(programs[1]).split();
        Assertions.assertEquals(t2.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "(", "or", "true",
                "false", ")", "true", ")")));
    }
    @Test
    public void testTokenize() {
        final Tokenizer t1 = new Tokenizer(programs[2]).split().tokenize();
        final Thing result1 = new Thing("+", new ArrayList<Thing>(Arrays.asList(new Thing("1",
                null), new Thing("2", null))));
        Assertions.assertTrue(t1.getThing().equals(result1));

        final Tokenizer t2 = new Tokenizer(programs[0]).split().tokenize();
        final Thing result2 = new Thing("and", new ArrayList<Thing>(Arrays.asList(new Thing("true",
                null), new Thing("true", null))));
        Assertions.assertTrue(t2.getThing().equals(result2));
    }
}
