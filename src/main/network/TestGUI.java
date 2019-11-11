package network;

import ui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TestGUI {
    private static final String url = "http://127.0.0.1:27999/exec?program=";

    private static void assertEquals(String a, String b) {
        if (!a.equals(b)) {
            throw new RuntimeException("Expected " + a + ", actually" + b);
        }
    }

    public static void main(String[] args) throws IOException {
        GUI.makeHttpServer();
        String result = httpGet(new URL(url + "(pow%202%204)"));
        assertEquals(result.trim(), "16");
        String result2 = httpGet(new URL(url + "(=%202%204)"));
        assertEquals(result2.trim(), "false");
        // TODO delete next 4 lines after deliverable 10
        String deliverable10 = httpGet(new URL(url + "deliverable10"));
        System.out.println("The JSON obtained from the API is: " + deliverable10);
        double a = Double.parseDouble(deliverable10.split("}")[0].split(":")[1]);
        System.out.println("Value of a in the JSON is: " + a);
        System.exit(0);
    }

    private static String httpGet(URL url) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }
}
