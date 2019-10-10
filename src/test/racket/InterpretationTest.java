package racket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InterpretationTest {
    private final Interpreter interpreter = new Interpreter(null);

    private String eval(String program) {
        try {
            return interpreter.eval(new Tokenizer(program)
                    .split()
                    .tokenize()
                    .getThing()).toString();
        } catch (RacketSyntaxError e) {
            return null;
        }
    }

    @Test
    public void testError() {
        try {
            interpreter.eval(new Tokenizer("(asdf 123)")
                    .split()
                    .tokenize()
                    .getThing()).toString();
            Assertions.fail();
        } catch (RacketSyntaxError racketSyntaxError) {
            racketSyntaxError.printStackTrace();
        }
    }

    @Test
    public void testCheckExpect() throws RacketSyntaxError {
        Assertions.assertEquals(eval("(check-expect 1 1)"), "(void)");
        Assertions.assertNull(interpreter.eval(new Tokenizer("(check-expect 1 2)")
                .split()
                .tokenize()
                .getThing()));
    }

    @Test
    public void doRacketTests() {
        try {
            interpreter.loadFile(Paths.get(System.getProperty("user.dir") + "/lib/test.rkt"));
        } catch (IOException | RacketSyntaxError e) {
            e.printStackTrace();
        }
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
    public void testOr() {
        Assertions.assertEquals(eval("(or true false)"), "true");
        Assertions.assertEquals(eval("(or false false)"), "false");
    }

    @Test
    public void testMath() {
        Assertions.assertEquals(eval("(+ 1 2)"), "3");
        Assertions.assertEquals(eval("(+ 3 4)"), "7");
        Assertions.assertEquals(eval("(+ 3 -4)"), "-1");
        Assertions.assertEquals(eval("(- 1 2)"), "-1");
        Assertions.assertEquals(eval("(- 2 1)"), "1");
        Assertions.assertEquals(eval("(- 1 -2)"), "3");
        Assertions.assertEquals(eval("(* 1 2)"), "2");
        Assertions.assertEquals(eval("(* 2 1)"), "2");
        Assertions.assertEquals(eval("(* 1 -2)"), "-2");
        Assertions.assertEquals(eval("(pow 2 3)"), "8");
        Assertions.assertEquals(eval("(pow 2 4)"), "16");
    }

    @Test
    public void testDefineVariables() {
        eval("(define x true)");
        Assertions.assertEquals(eval("x"), "true");
        eval("(define y (+ 1 1))");
        Assertions.assertEquals(eval("y"), "2");
    }

    @Test
    public void testDefineFunctions() {
        eval("(define (test-func test-param-a test-param-b) " +
                "(and test-param-a (not test-param-b)))");
        Assertions.assertEquals(eval("(test-func true false)"), "true");
        Assertions.assertEquals(eval("(test-func false false)"), "false");
        eval("(define (test-func-2 test-param-a test-param-b) " +
                "(> (abs test-param-a) (abs test-param-b))");
        Assertions.assertEquals(eval("(test-func-2 1 1)"), "false");
        Assertions.assertEquals(eval("(test-func-2 2 -1)"), "true");
        try {
            interpreter.eval(new Tokenizer("(test-func 1)")
                    .split()
                    .tokenize()
                    .getThing()).toString();
            Assertions.fail();
        } catch (RacketSyntaxError racketSyntaxError) {
            Assertions.assertEquals(racketSyntaxError.getMessage(), "test-func: expects 2 arguments, but found 1");
        }
    }

    @Test
    public void testDefineRecursiveFunctions() {
        eval("(define (rec x) (if (= x 0) x (rec (- x 1))))");
        Assertions.assertEquals(eval("(rec 10)"), "0");
    }

    @Test
    public void testIf() {
        Assertions.assertEquals(eval("(if true 1 2)"), "1");
        Assertions.assertEquals(eval("(if false 1 2)"), "2");
    }

    @Test
    public void testCompare() {
        Assertions.assertEquals(eval("(> 1 1)"), "false");
        Assertions.assertEquals(eval("(> 2 1)"), "true");
        Assertions.assertEquals(eval("(> 1 2)"), "false");
        Assertions.assertEquals(eval("(< 1 1)"), "false");
        Assertions.assertEquals(eval("(< 1 2)"), "true");
        Assertions.assertEquals(eval("(< 2 1)"), "false");
    }

    @Test
    public void testWriteFile() throws IOException {
        final Path path = Paths.get(System.getProperty("user.dir") + "/lib/result");
        interpreter.writeFile(path, "1");
        final String content = new String(Files.readAllBytes(path));
        Assertions.assertEquals(content, "1\n");

        eval("(save! true)");
        final String content2 = new String(Files.readAllBytes(path));
        Assertions.assertEquals(content2, "true\n");
    }

    @Test
    public void testLoadFile() {
        Assertions.assertEquals(eval("(abs -1)"), "1");
        Assertions.assertEquals(eval("(abs 1)"), "1");
        Assertions.assertEquals(eval("(abs 0)"), "0");
    }
}