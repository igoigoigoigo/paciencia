package game;

import javax.swing.*;

public class CardGame extends JFrame {
    public CardGame() {
        setTitle("Paciência");
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        add(new CardSpriteHolder());
    }

    public void start() {
        setVisible(true);
    }
}
