package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class CardType {

    public enum Suit {
        Hearts, Diamonds, Clubs, Spades
    }

    public enum Color {
        Red, Black
    }

    public enum Value {
        Ace, V2, V3, V4, V5, V6, V7, V8, V9, V10, Jack, Queen, King
    }

    private final Suit suit;
    private final Color color;
    private final Value value;

    public CardType(Suit suit, Color color, Value value) {
        this.suit = suit;
        this.color = color;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CardType)) return false;
        CardType other = (CardType) obj;
        return suit == other.suit && color == other.color && value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, color, value);
    }

    public static boolean isImediatlyGreater(Value v1, Value v2) {
        return v1.ordinal() + 1 == v2.ordinal();
    }

    public static final List<CardType> FULL_DECK;

    static {
        List<CardType> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            Color color = (suit == Suit.Hearts || suit == Suit.Diamonds) ? Color.Red : Color.Black;
            for (Value value : Value.values()) {
                deck.add(new CardType(suit, color, value));
            }
        }
        FULL_DECK = Collections.unmodifiableList(deck);
    }
}
