package ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import racket.AbstractRacketError;
import racket.Interpreter;
import racket.RacketLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class GUI {
    // This class was influenced by an implementation found here:
    // https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api
    private static final boolean DEBUG = true;
    private static final int PORT = 27999;

    public static void main(String[] args) throws IOException {
        makeHttpServer();
        final Scanner sc = new Scanner(System.in);
        System.out.println("\nSpawn the GUI client? y/n");
        System.out.print("> ");
        if (sc.nextLine().trim().equals("y")) {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "/lib/gui/start.sh");
        } else {
            System.exit(0);
        }
    }

    // REQUIRES: Port *:this.PORT is open
    // EFFECTS: Opens HTTP server on *:this.PORT
    //
    //          Supported methods and entry points:
    //            GET /exec
    //              Parameters:
    //                program: String
    //              Description:
    //                evaluates a racket program, returns the string value of the result

    public static void makeHttpServer() throws IOException {
        System.out.println("Racket interpretation server listening on http://127.0.0.1:" + PORT);
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/exec", new ExecHandler());
        server.setExecutor(null);
        server.start();
    }

    private static class ExecHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange req) throws IOException {
            final String program = req.getRequestURI().getQuery().substring(8);
            final String response = eval(program);
            req.sendResponseHeaders(200, response.length());
            OutputStream output = req.getResponseBody();
            output.write(response.getBytes());
            output.close();
        }

        private String eval(String program) {
            final Interpreter interpreter = new Interpreter(null);
            RacketLogger logger = new RacketLogger();
            if (DEBUG) {
                interpreter.on("programBeforeExecuted", logger);
            }
            try {
                String ret = interpreter.execProgram(program).toString();
                for (String entry : logger.getLog()) {
                    System.out.println("[RacketLogger] " + entry);
                }
                return ret;
            } catch (AbstractRacketError e) {
                return e.toString();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return "Caught RuntimeException, check console!";
            }
        }
    }
}
