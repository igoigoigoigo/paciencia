package stack;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;

import game.Card;
import stack.Hand;
import util.Point;

public abstract class CardStack {
    public static final double DefaultCardsMargin = 20.0f;
    public static final double MaxStackHeight = 280.0;

    public abstract void add(Card card);
    public abstract Card peek();
    public abstract Card pop();
    public abstract void drop(Hand hand);
    public abstract void remove(Hand hand);
    public abstract int size();
    public abstract Hand pick(Point p);
    public abstract boolean isValidTop(Hand hand);
    public abstract Point getTopPosition();
    public abstract boolean isOver(Point p);
    public abstract void draw(GraphicsContext ctx);
}
