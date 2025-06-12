package stack;

import game.Card;
import util.Point;

import java.util.List;

public class Hand {
    private final List<Card> cards;
    private final Point position;

    public Hand(List<Card> cards, Point position) {
        this.cards = cards;
        this.position = position;
    }

    public void move(int dx, int dy) {
        position.translate(dx, dy);
        for (Card card : cards) {
            card.getPosition().translate(dx, dy);
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public Point getPosition() {
        return position;
    }
}
