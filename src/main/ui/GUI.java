package ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import racket.AbstractRacketError;
import racket.Interpreter;
import racket.RacketSyntaxError;
import racket.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GUI {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(27999), 0);
        server.createContext("/exec", new ExecHandler());
        server.setExecutor(null);
        server.start();
        Runtime.getRuntime().exec("/Users/Frank_L/Desktop/gui/start.sh");
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
                return e.getMessage();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return "Java Backend: Caught RuntimeException, check console!";
            }
        }
    }
}
