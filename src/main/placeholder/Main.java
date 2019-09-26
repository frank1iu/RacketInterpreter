package placeholder;

import racket.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RacketSyntaxError {
        Scanner scanner = new Scanner(System.in);
        final Interpreter interpreter = new Interpreter(null);
        while (true) {
            System.out.print("> ");
            final String result = interpreter.eval(new Tokenizer(scanner.nextLine())
                    .split()
                    .tokenize()
                    .getThing())
                    .getValue()
                    .toString();
            System.out.println(result);
        }
    }
}
