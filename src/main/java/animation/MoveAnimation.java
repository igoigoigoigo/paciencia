package animation;

import util.Point;

public class MoveAnimation implements Animation {
    private final Point point;
    private final int dx, dy;
    private int steps;

    public MoveAnimation(Point point, int dx, int dy) {
        this.point = point;
        this.dx = dx / 10;
        this.dy = dy / 10;
        this.steps = 10;
    }

    @Override
    public void update() {
        point.translate(dx, dy);
        steps--;
    }

    @Override
    public boolean isOver() {
        return steps <= 0;
    }
}
