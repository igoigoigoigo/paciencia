package stack;

import animation.Animation;
import animation.MoveAnimation;
import animation.AnimationPool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import stack.CardStack;
import util.Point;
import game.Card;
import game.CardType;
import datastructures.MyStack;

public class DepositStack extends CardStack {

    private Point position;
    private AnimationPool animationPool;
    private Card placeholderCard;
    private MyStack<Card> cards;

    public DepositStack(AnimationPool animationPool, Point position) {
        this.animationPool = animationPool;
        this.position = position;
        this.cards = new MyStack<>();
        this.placeholderCard = new Card(new CardType(CardType.Suit.Hearts, CardType.Color.Red, CardType.Value.Ace));
        this.placeholderCard.setPosition(this.position);
    }

    @Override
    public void add(Card card) {
        animationPool.add(new MoveAnimation(card, this.position, Animation.MoveTime));
        this.cards.push(card);
    }

    @Override
    public Hand pick(Point p) {
        Hand hand = new Hand();
        if (this.cards.size() > 0 && this.cards.peek().isOver(p)) {
            hand.add(this.cards.peek());
        }

        return hand;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        if (cards.size() > 1)
            cards.peek().draw(ctx);
        else {
            ctx.setFill(new Color(0, 0, 0, 0.2f));
            ctx.fillRoundRect(this.position.getX() - Card.Width / 2, this.position.getY() - Card.Height / 2, Card.Width,
                    Card.Height, Card.Border, Card.Border);
        }

        if (cards.size() > 0)
            cards.peek().draw(ctx);
    }

    @Override
    public boolean isOver(Point p) {
        return this.placeholderCard.isOver(p);
    }

    @Override
    public boolean isValidTop(Hand hand) {

        if (hand.size() != 1)
            return false;

        Card card = hand.bottom();

        if (this.cards.size() > 0) {
            Card top = this.cards.peek();
            if (top.getSuit() == card.getSuit() && CardType.isImediatlyGreater(top.getValue(), card.getValue()))
                return true;
        } else if (card.getValue() == CardType.Value.Ace)
            return true;

        return false;
    }

    @Override
    public Point getTopPosition() {
        return (Point) this.position.clone();
    }

    @Override
    public int size() {
        return this.cards.size();
    }

    @Override
    public Card pop() {
        return this.cards.pop();
    }

    @Override
    public Card peek() {
        return this.cards.peek();
    }

    @Override
    public void remove(Hand hand) {
        if (hand.size() == 1) {
            Card cardToRemove = hand.bottom();
            if (cardToRemove != null && !this.cards.isEmpty() && this.cards.peek() == cardToRemove) {
                this.cards.pop();
            }
        }
    }

    @Override
    public void drop(Hand hand) {
        for (Card card : hand.getCards()) {
            this.add(card);
        }
    }
}
