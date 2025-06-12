package animation;

import game.Card;

public class FlipAnimation implements Animation {
    private final Card card;
    private int count;

    public FlipAnimation(Card card) {
        this.card = card;
        this.count = 10;
    }

    @Override
    public void update() {
        if (count == 5) card.toggle();
        count--;
    }

    @Override
    public boolean isOver() {
        return count <= 0;
    }
}
