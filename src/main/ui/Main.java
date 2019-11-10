package ui;

import racket.*;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Interpreter interpreter = new Interpreter();
    private static final RacketLogger logger = new RacketLogger();

    public static void main(String[] args) {
        interpreter.on("programBeforeExecuted", logger);
        interpreter.getContext().on("containsKeyQuery", logger);
        interpreter.getContext().on("getQuerySuccess", logger);
        while (true) {
            try {
                System.out.print("> ");
                final Thing thing = interpreter.eval(new Tokenizer(scanner.nextLine()).split().tokenize().getThing());
                for (String entry : logger.getLog()) {
                    System.out.println("[RacketLogger] " + entry);
                }
                System.out.println("[Result] (racket.Type." + thing.getType().toString() + ") " + thing.toString());
                logger.getLog().clear();
            } catch (AbstractRacketError e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
