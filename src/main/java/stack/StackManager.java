package stack;

import animation.Animation;
import animation.AnimationPool;
import animation.HandMoveAnimation;
import util.Point;

import javafx.scene.canvas.GraphicsContext;

import java.util.Iterator;

import datastructures.MyLinkedList;

public class StackManager {
    private MyLinkedList<CardStack> stacks;
    private CardGenerator cardGenerator;
    private AnimationPool animationPool;

    private CardStack pickedStack;
    private Hand pickedCards;
    private Point lastCardPosition;
    private Point lastPickPosition;

    public StackManager(AnimationPool animationPool) {
        this.stacks = new MyLinkedList<>();
        this.animationPool = animationPool;
        this.cardGenerator = new CardGenerator();
        this.pickedCards = null;
    }

    public void drag(double x, double y) {
        if (pickedCards != null) {
            Point offset = new Point(x, y).minus(lastPickPosition);
            pickedCards.setPosition(lastCardPosition.add(offset));
        }
    }

    public void addSimpleStack(Point position, int n) throws CardGeneratorEmptyException {
        SimpleStack stack = new SimpleStack(this.animationPool, position);
        this.cardGenerator.generate(stack, n);
        stack.flipFirst();
        this.stacks.addLast(stack);
    }

    public void addDepositStack(Point position) {
        stacks.addLast(new DepositStack(this.animationPool, position));
    }

    public void addRestStack(Point position) throws CardGeneratorEmptyException {
        RestStack stack = new RestStack(this.animationPool, position);
        this.cardGenerator.rest(stack);
        this.stacks.addLast(stack);
    }

    public void pick(double x, double y) {
        for (Iterator<CardStack> it = this.stacks.iterator(); it.hasNext(); ) {
            CardStack stack = it.next();
            if (stack.size() > 0 && stack.isOver(new Point(x, y))) {
                this.pickedStack = stack;
                this.pickedCards = stack.pick(new Point(x, y));
                this.lastCardPosition = this.pickedCards.getPosition();
                this.lastPickPosition = new Point(x, y);
                break;
            }
        }
    }

    public void drop(double x, double y) {
        if (this.pickedCards != null) {
            boolean dropped = false;
            for (Iterator<CardStack> it = this.stacks.iterator(); it.hasNext(); ) {
                CardStack stack = it.next();
                if (this.pickedStack != stack && stack.isOver(new Point(x, y)) && stack.isValidTop(this.pickedCards)) {
                    this.pickedStack.remove(this.pickedCards);
                    stack.drop(this.pickedCards);
                    dropped = true;
                    break;
                }
            }
            if (!dropped) {
                this.animationPool.add(
                        new HandMoveAnimation(this.pickedCards, this.pickedStack.getTopPosition(), Animation.MoveTime));
            }
            this.pickedCards = null;
            this.lastCardPosition = null;
            this.lastPickPosition = null;
        }
    }

    public void move(double x, double y) {
        if (this.pickedCards != null) {
            Point newPosition = this.lastCardPosition.add(new Point(x, y)).minus(this.lastPickPosition);
            this.pickedCards.setPosition(newPosition);
        }
    }

    public void draw(GraphicsContext ctx) {
        for (Iterator<CardStack> it = this.stacks.iterator(); it.hasNext(); ) {
            it.next().draw(ctx);
        }
        if (this.pickedCards != null)
            this.pickedCards.draw(ctx);
    }
}
