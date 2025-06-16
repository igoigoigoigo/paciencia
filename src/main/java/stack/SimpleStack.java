package stack;

import animation.Animation;
import animation.FlipAnimation;
import animation.FlipAnimation.FlipType;
import animation.MoveAnimation;
import animation.AnimationPool;
import javafx.scene.canvas.GraphicsContext;
import game.Card;
import game.CardType;
import util.Point;
import datastructures.MyLinkedList;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;

public class SimpleStack extends CardStack {

    private AnimationPool animationPool;
    private Point position;
    private double cardsMargin;
    private Card placeholderCard;
    private int topIndex;
    private MyLinkedList<Card> cards;

    public SimpleStack(AnimationPool animationPool, Point position) {
        this.animationPool = animationPool;
        this.position = position;
        this.cardsMargin = CardStack.DefaultCardsMargin;

        this.cards = new MyLinkedList<>();

        CardType tipoCard = new CardType(CardType.Suit.Hearts, CardType.Color.Red, CardType.Value.Ace);
        this.placeholderCard = new Card(tipoCard);
        this.placeholderCard.setPosition(this.position);
        this.topIndex = 0;
    }

    @Override
    public void add(Card card) {
        this.cards.addLast(card);
        this.topIndex = this.cards.size() - 1;

        Point topPosition = this.getTopPosition();
        animationPool.add(new MoveAnimation(card, topPosition, Animation.MoveTime));

        this.updateCardsPositions();
    }

    @Override
    public Card peek() {
        return this.cards.peekLast();
    }

    @Override
    public Card pop() {
        Card card = this.cards.pollLast();
        this.topIndex = this.cards.size() - 1;

        if (this.cards.size() > 0 && !this.peek().isFlipped()) {
            animationPool.add(new FlipAnimation(this.peek(), FlipType.SHOW, Animation.FlipTime));
            this.peek().flip();
        }

        return card;
    }

    @Override
    public Hand pick(Point p) {
        Hand hand = new Hand();
        if (this.cards.isEmpty()) return hand;

        // Iterar do topo para a base
        int index = this.cards.size() - 1;
        int clickedIndex = -1;
        java.util.Iterator<Card> it = this.cards.reverseIterator();
        while (it.hasNext()) {
            Card card = it.next();
            if (card.isOver(p) && card.isFlipped()) {
                clickedIndex = index;
                break;
            }
            index--;
        }
        // Se encontrou uma carta válida, adiciona todas a partir dela até o topo
        if (clickedIndex != -1) {
            int i = 0;
            for (java.util.Iterator<Card> it2 = this.cards.iterator(); it2.hasNext(); ) {
                Card card = it2.next();
                if (i >= clickedIndex) hand.add(card);
                i++;
            }
        }
        this.topIndex = this.cards.size() - hand.size();
        return hand;
    }

    @Override
    public void remove(Hand hand) {
        List<Card> cardsToRemove = hand.getCards();
        if (cardsToRemove.isEmpty()) return;

        // Create a temporary list to store cards we want to keep
        MyLinkedList<Card> tempList = new MyLinkedList<>();
        
        // Iterate through all cards
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext();) {
            Card card = it.next();
            boolean shouldKeep = true;
            
            // Check if this card should be removed
            for (Card cardToRemove : cardsToRemove) {
                if (card == cardToRemove) {
                    shouldKeep = false;
                    break;
                }
            }
            
            if (shouldKeep) {
                tempList.addLast(card);
            }
        }
        
        // Replace the original list with our filtered list
        this.cards = tempList;

        if (this.cards.size() > 0 && !this.peek().isFlipped()) {
            animationPool.add(new FlipAnimation(this.peek(), FlipType.SHOW, Animation.FlipTime));
            this.peek().flip();
        }
        this.topIndex = this.cards.size() - 1;
    }

    @Override
    public void drop(Hand hand) {
        for (Card card : hand.getCards()) {
            this.add(card);
        }
        this.topIndex = this.cards.size() - 1;
        this.updateCardsPositions();
    }

    public void flipFirst() {
        if (this.cards.size() > 0) {
            this.peek().flip();
            this.peek().setAngle(0);
        }
    }

    @Override
    public Point getTopPosition() {
        return this.position.add(new Point(0, this.cardsMargin * (this.topIndex)));
    }

    @Override
    public void draw(GraphicsContext ctx) {
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            it.next().draw(ctx);
        }
    }

    @Override
    public boolean isOver(Point p) {
        boolean over = false;
        if (this.cards.size() > 0) {
            for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
                if (it.next().isOver(p))
                    over = true;
            }
        } else
            over = this.placeholderCard.isOver(p);
        return over;
    }

    @Override
    public boolean isValidTop(Hand hand) {
        boolean isValid = false;

        Card card = hand.bottom();
        if (this.cards.size() > 0) {
            Card top = this.peek();
            if (top.getColor() != card.getColor() && CardType.isImediatlyGreater(card.getValue(), top.getValue()))
                isValid = true;
        } else if (card.getValue() == CardType.Value.King)
            isValid = true;

        return isValid;
    }

    @Override
    public int size() {
        return this.cards.size();
    }

    private void updateCardsPositions() {
        int numOfCards = this.cards.size();
        double stackHeight = this.cardsMargin * numOfCards + Card.Height;

        if (stackHeight > CardStack.MaxStackHeight) {
            this.cardsMargin = (MaxStackHeight - Card.Height) / numOfCards;
        }

        Point cardPosition = this.position;
        for (java.util.Iterator<Card> it = this.cards.iterator(); it.hasNext(); ) {
            Card card = it.next();
            animationPool.add(new MoveAnimation(card, cardPosition, Animation.MoveTime));
            cardPosition = cardPosition.add(new Point(0, this.cardsMargin));
        }
    }
}
