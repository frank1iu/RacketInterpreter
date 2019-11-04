package ui;

import racket.*;

import java.util.Scanner;

public class Main {
    /*
    Deliverable 9
    High coupling: racket.Thing, racket.RacketPair, racket.RacketFunc have very
                   similar toString() methods, which can possibly be extracted
    Low cohesion: racket.Thing covers all types except pairs and functions.
                  maybe make a racket.RacketInteger, racket.RacketBoolean, etc
    Cohesion assessment:
        For class racket.Tokenizer:
        Fields: String program, ArrayList split, Thing thing
        Methods:
            Tokenizer(), split(), tokenize()
        I have realized that racket.Tokenizer should not be responsible for
        processing strings. I will refactor to keep the behavior the same, but
        use another class, racket.StringSplitter, to process strings.
        !!! TODO
    Coupling analysis:
        For classes racket.
     */

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
