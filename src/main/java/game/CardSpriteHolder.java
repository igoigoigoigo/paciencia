package game;

import util.Rectangle;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public final class CardSpriteHolder {

    private static final String backImageFilename = "./sprites/playingCardBacks.png";
    private static final String cardsImageFilename = "./sprites/playingCards.png";

    private static Image imageCards;
    private static Image imageBack;

    private static final Rectangle backBound = new Rectangle(140 * 2, 0, 140, 190);

    private static final HashMap<CardType, Rectangle> cardsBounds = new HashMap<>();

    static {
        initCardBounds();
    }

    public static Image getBackImage() {
        if (imageBack == null) {
            try {
                FileInputStream stream = new FileInputStream(backImageFilename);
                imageBack = new Image(stream);
            } catch (FileNotFoundException fnfe) {
                System.out.println("Error opening image file: " + fnfe.getMessage());
            }
        }
        return imageBack;
    }

    public static Image getCardsImage() {
        if (imageCards == null) {
            try {
                FileInputStream stream = new FileInputStream(cardsImageFilename);
                imageCards = new Image(stream);
            } catch (FileNotFoundException fnfe) {
                System.out.println("Error opening image file: " + fnfe.getMessage());
            }
        }
        return imageCards;
    }

    public static Rectangle getBackBound() {
        return backBound;
    }

    public static Rectangle getCardBound(CardType type) {
        return cardsBounds.get(type);
    }

    private static void initCardBounds() {
        mapSuit(CardType.Suit.Hearts, CardType.Color.Red, new int[][]{
                {140, 190 * 7}, {140 * 5, 190 * 2}, {140 * 2, 190 * 5}, {140 * 2, 190 * 4},
                {140 * 2, 190 * 3}, {140 * 2, 190 * 2}, {140 * 2, 190 * 1}, {140 * 2, 0},
                {140 * 1, 190 * 9}, {140 * 1, 190 * 8}, {140 * 1, 190 * 6}, {140 * 1, 190 * 4}, {140 * 1, 190 * 5}
        });

        mapSuit(CardType.Suit.Diamonds, CardType.Color.Red, new int[][]{
                {140 * 3, 0}, {140 * 3, 190 * 9}, {140 * 3, 190 * 8}, {140 * 3, 190 * 7},
                {140 * 3, 190 * 6}, {140 * 3, 190 * 5}, {140 * 3, 190 * 4}, {140 * 3, 190 * 3},
                {140 * 3, 190 * 2}, {140 * 3, 190 * 1}, {140 * 2, 190 * 9}, {140 * 2, 190 * 7}, {140 * 2, 190 * 8}
        });

        mapSuit(CardType.Suit.Clubs, CardType.Color.Black, new int[][]{
                {140 * 4, 190 * 3}, {140 * 2, 190 * 6}, {140 * 5, 190 * 1}, {140 * 5, 0},
                {140 * 4, 190 * 9}, {140 * 4, 190 * 8}, {140 * 4, 190 * 7}, {140 * 4, 190 * 6},
                {140 * 4, 190 * 5}, {140 * 4, 190 * 4}, {140 * 4, 190 * 2}, {140 * 4, 0}, {140 * 4, 190 * 1}
        });

        mapSuit(CardType.Suit.Spades, CardType.Color.Black, new int[][]{
                {0, 190 * 3}, {140, 190 * 2}, {140, 190 * 1}, {140, 0},
                {0, 190 * 9}, {0, 190 * 8}, {0, 190 * 7}, {0, 190 * 6},
                {0, 190 * 5}, {0, 190 * 4}, {0, 190 * 2}, {0, 0}, {0, 190}
        });
    }

    private static void mapSuit(CardType.Suit suit, CardType.Color color, int[][] positions) {
        CardType.Value[] values = CardType.Value.values();
        for (int i = 0; i < positions.length && i < values.length; i++) {
            cardsBounds.put(
                    new CardType(suit, color, values[i]),
                    new Rectangle(positions[i][0], positions[i][1], 140, 190)
            );
        }
    }
}
