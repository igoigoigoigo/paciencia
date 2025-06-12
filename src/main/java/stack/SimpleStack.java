package stack;

import game.Card;
import util.Point;

public class SimpleStack extends CardStack {
    public SimpleStack(Point position) {
        super(position);
    }

    @Override
    public boolean canAdd(Card card) {
        if (cards.isEmpty()) return card.getNumber() == 13; // rei
        Card top = cards.get(cards.size() - 1);
        return top.getNumber() == card.getNumber() + 1 &&
                top.getType() != card.getType(); // alternância de cores
    }
}
