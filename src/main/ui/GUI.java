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

public class GUI {
    private static final boolean DEBUG = false;

    public static void main(String[] args) throws IOException {
        makeHttpServer();
        Runtime.getRuntime().exec(System.getProperty("user.dir") + "/lib/gui/start.sh");
    }

    // REQUIRES: Port 27999 is open
    // EFFECTS: Opens HTTP server on *:27999 that listens to racket execution on /exec

    public static void makeHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(27999), 0);
        server.createContext("/exec", new ExecHandler());
        server.setExecutor(null);
        server.start();
    }

    static class ExecHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange req) throws IOException {
            final String program = req.getRequestURI().getQuery().substring(8);
            final String response = eval(program);
            req.sendResponseHeaders(200, response.length());
            OutputStream os = req.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String eval(String program) {
            // TODO delete the if{} after deliverable 10
            if (program.equals("deliverable10")) {
                return "{\"a\": " + Math.random() + "}";
            }
            final Interpreter interpreter = new Interpreter(null);
            //RacketLogger logger = new RacketLogger();
            //if (DEBUG) {
            //    interpreter.on("programBeforeExecuted", logger);
            //}
            try {
                String ret = interpreter.execProgram(program).toString();
                //for (String entry : logger.getLog()) {
                //    System.out.println("[RacketLogger] " + entry);
                //}
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
