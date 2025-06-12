package stack;

import game.Card;
import game.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardGenerator {
    private final List<Card> cards = new ArrayList<>();

    public CardGenerator() {
        for (CardType type : CardType.values()) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(type, i));
            }
        }
        Collections.shuffle(cards);
    }

    public Card getNextCard() {
        if (cards.isEmpty()) {
            throw new CardGeneratorEmptyException();
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
