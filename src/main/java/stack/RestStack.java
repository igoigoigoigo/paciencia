package stack;

import animation.Animation;
import animation.AnimationPool;
import animation.FlipAnimation;
import animation.FlipAnimation.FlipType;
import animation.MoveAnimation;
import game.Card;
import game.CardType;
import javafx.scene.canvas.GraphicsContext;
import util.Point;
import datastructures.MyQueue;

public class RestStack extends CardStack {
    private static final double HorizontalOffset = 200.0;
    private static final int NumShowingCards = 4;

    private Point position;
    private AnimationPool animationPool;
    private Card placeholderCard;
    private int topIndex;
    private MyQueue<Card> cards;

    public RestStack(AnimationPool animationPool, Point position) {
        this.position = position;
        this.animationPool = animationPool;
        this.cards = new MyQueue<>();
        CardType placeholderType = new CardType(CardType.Suit.Hearts, CardType.Color.Red, CardType.Value.Ace);
        this.placeholderCard = new Card(placeholderType);
        this.placeholderCard.setPosition(position);
        this.topIndex = -1;
    }

    @Override
    public void add(Card card) {
        this.cards.add(card);
        this.animationPool.add(new MoveAnimation(card, this.position, Animation.MoveTime));
    }

    private Card getTopCard() {
        if (this.topIndex < 0 || this.cards.isEmpty()) return null;
        return this.cards.get(this.topIndex);
    }

    private Hand pickFirstCard() {
        Hand result = new Hand();
        Card topCard = this.getTopCard();
        if (topCard != null) {
            result.add(topCard);
        }
        return result;
    }

    private void shiftCards() {
        this.topIndex++;
        if (this.topIndex >= this.cards.size()) {
            this.topIndex = -1;
            this.resetStack();
        } else {
            Card card = this.getTopCard();
            if (card != null && !card.isFlipped()) {
                card.flip();
            }
            this.updateCardsPosition();
        }
    }

    private void updateCardsPosition() {
        if (this.topIndex < 0) return;
        
        // Update position of the top card
        Card card = this.getTopCard();
        if (card != null) {
            this.animationPool.add(new MoveAnimation(card, 
                this.position.add(new Point(HorizontalOffset, 0)), 
                Animation.MoveTime));
        }
        
        // Update positions of cards below
        for (int i = 0; i < NumShowingCards; i++) {
            int index = this.topIndex - i - 1;
            if (index >= 0) {
                Card c = this.cards.get(index);
                if (c != null) {
                    this.animationPool.add(new MoveAnimation(c,
                        this.position.add(new Point(HorizontalOffset - CardStack.DefaultCardsMargin * (i + 1), 0)),
                        Animation.MoveTime));
                }
            }
        }
    }

    private void resetStack() {
        for (int i = 0; i < this.cards.size(); i++) {
            Card card = this.cards.get(i);
            if (card != null) {
                this.animationPool.add(new MoveAnimation(card, this.position, Animation.MoveTime));
                if (card.isFlipped()) {
                    card.flip();
                    this.animationPool.add(new FlipAnimation(card, FlipType.HIDE, Animation.MoveTime));
                }
            }
        }
    }

    @Override
    public Hand pick(Point p) {
        Hand result = new Hand();
        if (this.placeholderCard.isOver(p)) {
            this.shiftCards();
        } else if (this.topIndex >= 0) {
            Card topCard = this.getTopCard();
            if (topCard != null && topCard.isOver(p)) {
                result = pickFirstCard();
            }
        }
        return result;
    }

    @Override
    public boolean isOver(Point p) {
        if (this.placeholderCard.isOver(p)) return true;
        if (this.topIndex >= 0) {
            Card topCard = this.getTopCard();
            return topCard != null && topCard.isOver(p);
        }
        return false;
    }

    @Override
    public Point getTopPosition() {
        if (this.topIndex >= 0)
            return this.position.add(new Point(HorizontalOffset, 0));
        return (Point) this.position.clone();
    }

    @Override
    public void remove(Hand hand) {
        if (hand.size() == 1) {
            Card topCard = this.getTopCard();
            if (topCard != null && hand.bottom().equals(topCard)) {
                // Create a temporary queue to store cards we want to keep
                MyQueue<Card> tempQueue = new MyQueue<>();
                
                // Add all cards except the one being removed
                for (int i = 0; i < this.cards.size(); i++) {
                    Card card = this.cards.get(i);
                    if (card != topCard) {
                        tempQueue.add(card);
                    }
                }
                
                // Replace the original queue with our filtered queue
                this.cards = tempQueue;
                this.topIndex--;
                this.updateCardsPosition();
            }
        }
    }

    @Override
    public void drop(Hand hand) {
        for (Card card : hand.getCards()) {
            this.add(card);
        }
    }

    @Override
    public boolean isValidTop(Hand hand) {
        return false;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            it.next().draw(ctx);
        }
    }

    @Override
    public Card peek() {
        return this.cards.peek();
    }

    @Override
    public Card pop() {
        return this.cards.poll();
    }

    @Override
    public int size() {
        return this.cards.size();
    }
}
