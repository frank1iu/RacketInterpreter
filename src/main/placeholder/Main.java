package placeholder;

import racket.Interpreter;
import racket.Thing;
import racket.Tokenizer;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Interpreter interpreter = new Interpreter();

        while (true) {
            final String result = interpreter.eval(new Tokenizer(scanner.nextLine())
                    .split()
                    .tokenize()
                    .getTokens()
                    .get(0)).getValue().toString();
            System.out.println(result);
        }
    }
    public static void a() {
        System.out.println("a");
    }
    public static void b() {
        System.out.println("b");
    }
}
