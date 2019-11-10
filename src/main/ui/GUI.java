package ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import racket.AbstractRacketError;
import racket.Interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class GUI {
    public static void main(String[] args) throws IOException {
        makeServer();
        Runtime.getRuntime().exec("/Users/Frank_L/Desktop/gui/start.sh");
    }

    public static void makeServer() throws IOException {
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
            final Interpreter interpreter = new Interpreter(null);
            try {
                return interpreter.execProgram(program).toString();
            } catch (AbstractRacketError e) {
                return e.toString();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return "Caught RuntimeException, check console!";
            }
        }
    }
}
