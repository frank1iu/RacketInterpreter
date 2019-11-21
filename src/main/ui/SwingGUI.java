package ui;

import racket.AbstractRacketError;
import racket.Interpreter;
import racket.RacketLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingGUI {
    private static final String HTMLContent = "<html><head></head><body align=center><font size=50 bgcolor=lime>"
            + "Welcome to <font color=purple bgcolor=lime><b>Docter Racquet</b></font>!</font>"
            + "<br><font color=blue>Language: Bootleg Student Language with Lambda</font>"
            + "<br><i><font color=white bgcolor=black>\"Trust the Natural Recursion\"</font></i>"
            + "<hr><img src=\"http://127.0.0.1:8080/spin.gif\" />"
            + "<img src=\"http://127.0.0.1:8080/cat.png\" /><img src=\"http://127.0.0.1:8080/spin.gif\" />"
            + "</body></html>";

    private static final String HTMLResultAreaContent = "<html><body align=center><font color=green>"
            + "Result: [PRESS EVALUATE]</font></body></html>";

    public static void main(String[] args) {
        JFrame window = new JFrame("Docter Racquet");
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        JEditorPane resultText = makeEditorPane(HTMLResultAreaContent);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField textField = new JTextField(1);
        JButton evalButton = new JButton("Evaluate");
        evalButton.setActionCommand("eval");
        evalButton.addActionListener(new EvalListener(resultText, textField));
        window.add(makeEditorPane(HTMLContent));
        window.add(resultText);
        window.add(textField);
        window.add(evalButton);
        window.pack();
        window.setSize(new Dimension(800, 600));
        window.setVisible(true);
    }

    private static JEditorPane makeEditorPane(String content) {
        JEditorPane html = new JEditorPane();
        html.setContentType("text/html");
        html.setText(content);
        html.setEditable(false);
        return html;
    }

    // Some code is from:
    // https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application

    private static class EvalListener implements ActionListener {

        private JEditorPane label;
        private JTextField field;
        private Interpreter interpreter;
        private RacketLogger racketLogger;

        private EvalListener(JEditorPane resultText, JTextField field) {
            this.label = resultText;
            this.field = field;
            interpreter = new Interpreter();
            racketLogger = new RacketLogger();
            interpreter.on("programBeforeExecuted", racketLogger);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("eval")) {
                this.label.setText(HTMLResultAreaContent.replace("[PRESS EVALUATE]", eval(this.field.getText())));
                this.field.setText("");
            }
        }

        private String eval(String program) {
            try {
                String ret = interpreter.execProgram(program).toString();
                for (String entry : racketLogger.getLog()) {
                    System.out.println("[RacketLogger] " + entry);
                }
                racketLogger.getLog().clear();
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
