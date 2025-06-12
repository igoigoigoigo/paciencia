package stack;

import game.Card;
import game.CardType;

public class DepositStack extends CardStack {
    private final CardType type;

    public DepositStack(CardType type, util.Point position) {
        super(position);
        this.type = type;
    }

    @Override
    public boolean canAdd(Card card) {
        if (card.getType() != type) return false;
        if (cards.isEmpty()) return card.getNumber() == 1;
        Card top = cards.get(cards.size() - 1);
        return card.getNumber() == top.getNumber() + 1;
    }
}
