package stack;

import game.Card;
import util.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class CardStack {
    protected final List<Card> cards = new ArrayList<>();
    protected final Point position;

    public CardStack(Point position) {
        this.position = position;
    }

    public abstract boolean canAdd(Card card);

    public void addCard(Card card) {
        if (canAdd(card)) {
            cards.add(card);
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> getCards() {
        return cards;
    }

    public Point getPosition() {
        return position;
    }
}
