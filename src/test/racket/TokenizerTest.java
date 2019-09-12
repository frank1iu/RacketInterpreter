package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TokenizerTest {
    private final String[] programs = {"(and true true)", "(and true true) (or false false)"};
    @Test
    public void testSplit() {
        final Tokenizer t1 = new Tokenizer(programs[0]).split();
        Assertions.assertEquals(t1.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "true", "true", ")")));
        final Tokenizer t2 = new Tokenizer(programs[1]).split();
        Assertions.assertEquals(t2.getSplit(), new ArrayList<String>(Arrays.asList("(", "and", "true", "true", ")", "(", "or", "false", "false", ")")));
    }
    @Test
    public void testTokenize() {
        final Tokenizer t1 = new Tokenizer(programs[0]).split().tokenize();
        System.out.println(t1.getTokens());
        System.out.println(((Token) ((ArrayList<Object>) t1.getTokens().get(0)).get(1)).getValue());
    }
}
