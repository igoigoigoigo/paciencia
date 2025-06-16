package stack;

import java.util.List;
import java.util.ArrayList;

import stack.CardStack;

import game.Card;
import javafx.scene.canvas.GraphicsContext;
import util.Point;
import datastructures.MyLinkedList;

public class Hand {
    private MyLinkedList<Card> cards;

    public Hand() {
        this.cards = new MyLinkedList<>();
    }

    public void add(Card card) {
        this.cards.addLast(card);
    }

    public Card bottom() {
        if (this.cards.isEmpty()) return null;
        return this.cards.getHead().data;
    }

    public int size() {
        return this.cards.size();
    }

    public List<Card> getCards() {
        List<Card> list = new ArrayList<>();
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            list.add(it.next());
        }
        return list;
    }

    public void draw(GraphicsContext ctx) {
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            it.next().draw(ctx);
        }
    }

    public void setPosition(Point p) {
        Point pos = (Point) p.clone();
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            Card card = it.next();
            card.setPosition(pos);
            pos = pos.add(new Point(0, CardStack.DefaultCardsMargin));
        }
    }

    public Point getPosition() {
        if (this.cards.isEmpty()) return new Point(0, 0);
        return this.cards.getHead().data.getPosition();
    }
}
