package stack;

import game.Card;
import game.CardType;
import datastructures.MyLinkedList;

public class CardGenerator {
    private MyLinkedList<Card> cards;
    private MyLinkedList<CardType> cardsTypes;
    private int index;

    public CardGenerator() {
        this.cards = new MyLinkedList<>();
        this.cardsTypes = new MyLinkedList<>();
        
        // Preencher cardsTypes com todos os tipos do baralho
        for (CardType.Suit suit : CardType.Suit.values()) {
            CardType.Color color = (suit == CardType.Suit.Hearts || suit == CardType.Suit.Diamonds) ? CardType.Color.Red : CardType.Color.Black;
            for (CardType.Value value : CardType.Value.values()) {
                this.cardsTypes.addLast(new CardType(suit, color, value));
            }
        }
        
        // Embaralhar cardsTypes manualmente
        java.util.List<CardType> tempList = new java.util.ArrayList<>();
        for (java.util.Iterator<CardType> it = this.cardsTypes.iterator(); it.hasNext(); ) {
            tempList.add(it.next());
        }
        java.util.Collections.shuffle(tempList);
        
        // Criar as cartas com os tipos embaralhados
        for (CardType ct : tempList) {
            this.cards.addLast(new Card(ct));
        }
        
        this.index = 0;
    }

    public void generate(CardStack stack, int n) throws CardGeneratorEmptyException {
        for (int i = 0; i < n; i++) {
            if (this.cards.isEmpty())
                throw new CardGeneratorEmptyException("Sem cartas suficientes para gerar.");
            stack.add(this.cards.pollLast());
        }
    }

    public void rest(CardStack stack) throws CardGeneratorEmptyException {
        while (!this.cards.isEmpty()) {
            stack.add(this.cards.pollLast());
        }
    }
}
