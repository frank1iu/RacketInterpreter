package ui;

import javax.swing.*;
import java.awt.*;

public class SwingGUI {
    public static void main(String[] args) {
        JFrame window = new JFrame("Docter Racquet");
        JLabel text = new JLabel("Hello", JLabel.CENTER);
        JTextArea textArea = new JTextArea(5, 20);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.CYAN);
        textArea.add(text);
        window.add(textArea);
        window.add(text);
        window.pack();
        window.setSize(new Dimension(800, 600));
        window.setVisible(true);
    }
}
