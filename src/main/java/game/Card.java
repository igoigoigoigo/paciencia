package game;

import util.Point;

import java.awt.*;

public class Card {
    private final CardType type;
    private final int number;
    private boolean faceUp;
    private final Point position = new Point();

    public Card(CardType type, int number) {
        this.type = type;
        this.number = number;
        this.faceUp = false;
    }

    public void draw(Graphics g, Image image) {
        if (faceUp) {
            // Lógica para desenhar a carta virada para cima
        } else {
            // Lógica para desenhar a parte de trás
        }
    }

    public void toggle() {
        faceUp = !faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public CardType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public Point getPosition() {
        return position;
    }
}
