package stack;

import util.Point;

import java.util.ArrayList;
import java.util.List;

public class StackManager {
    private final List<CardStack> stacks = new ArrayList<>();

    public StackManager() {
        // Exemplo de criação de pilhas
        for (int i = 0; i < 7; i++) {
            stacks.add(new SimpleStack(new Point(100 + i * 100, 200)));
        }
    }

    public List<CardStack> getStacks() {
        return stacks;
    }

    public void addStack(CardStack stack) {
        stacks.add(stack);
    }
}
