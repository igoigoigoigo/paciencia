package stack;

public class CardGeneratorEmptyException extends RuntimeException {
    public CardGeneratorEmptyException() {
        super("Não há mais cartas para gerar.");
    }
}
