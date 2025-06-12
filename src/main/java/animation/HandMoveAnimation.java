package animation;

import stack.Hand;

public class HandMoveAnimation implements Animation {
    private final Hand hand;
    private final int dx, dy;
    private int steps;

    public HandMoveAnimation(Hand hand, int dx, int dy) {
        this.hand = hand;
        this.dx = dx / 10;
        this.dy = dy / 10;
        this.steps = 10;
    }

    @Override
    public void update() {
        hand.move(dx, dy);
        steps--;
    }

    @Override
    public boolean isOver() {
        return steps <= 0;
    }
}
