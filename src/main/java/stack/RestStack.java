package stack;

import game.Card;
import util.Point;

public class RestStack extends CardStack {
    public RestStack(Point position) {
        super(position);
    }

    @Override
    public boolean canAdd(Card card) {
        return true; // sem restrições específicas
    }
}
