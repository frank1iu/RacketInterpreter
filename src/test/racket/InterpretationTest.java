package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InterpretationTest {
    private final Interpreter interpreter = new Interpreter(null);
    private String eval(String program) {
        return interpreter.eval(new Tokenizer(program)
                .split()
                .tokenize()
                .getThing()).toString();
    }
    @Test
    public void testAnd() {
        Assertions.assertEquals(eval("(and true true)"), "true");
        Assertions.assertEquals(eval("(and true false)"), "false");
    }
    @Test
    public void testNot() {
        Assertions.assertEquals(eval("(not true)"), "false");
        Assertions.assertEquals(eval("(not false)"), "true");
    }
    @Test
    public void testAdd() {
        Assertions.assertEquals(eval("(+ 1 2)"), "3");
        Assertions.assertEquals(eval("(+ 3 4)"), "7");
        Assertions.assertEquals(eval("(+ 3 -4)"), "-1");
    }
    @Test
    public void testSub() {
        Assertions.assertEquals(eval("(- 1 2)"), "-1");
        Assertions.assertEquals(eval("(- 2 1)"), "1");
        Assertions.assertEquals(eval("(- 1 -2)"), "3");
    }
}
