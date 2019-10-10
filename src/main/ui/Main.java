package ui;

import racket.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RacketSyntaxError {
        final Scanner scanner = new Scanner(System.in);
        final Interpreter interpreter = new Interpreter(null);
        while (true) {
            System.out.print("> ");
            System.out.println(interpreter.eval(new Tokenizer(scanner.nextLine())
                    .split()
                    .tokenize()
                    .getThing())
                    .toString());
        }
    }
}
