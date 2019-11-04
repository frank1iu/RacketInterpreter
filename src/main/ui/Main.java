package ui;

import racket.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RacketSyntaxError {
        final Scanner scanner = new Scanner(System.in);
        final Interpreter interpreter = new Interpreter(null);
        while (true) {
            try {
                System.out.print("> ");
                System.out.println(interpreter.eval(new Tokenizer(scanner.nextLine())
                        .split()
                        .tokenize()
                        .getThing())
                        .toString());
            } catch (AbstractRacketError e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
