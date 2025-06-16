package game;

import animation.AnimationPool;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import stack.CardGeneratorEmptyException;
import stack.StackManager;
import util.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CardGame {
    public static final int Width = 760;
    public static final int Height = 480;
    private static final Color BgColor = new Color(0.43, 0.18, 0.23, 1.0);

    private final Canvas canvas;
    private StackManager stackManager;
    private ImageButton reloadButton;
    private AnimationPool animationPool;

    private double elapsedTimeSeconds = 0;

    private Image clockIcon;
    private static final double ClockIconSize = 30.0;
    private static final String ClockIconFilename = "./sprites/clock.png";

    public CardGame(Canvas canvas) {
        this.canvas = canvas;
        loadClockIcon();
        this.reset();
    }

    private void loadClockIcon() {
        try {
            FileInputStream stream = new FileInputStream(ClockIconFilename);
            this.clockIcon = new Image(stream);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar ícone do relógio: " + e.getMessage());
            this.clockIcon = null;
        }
    }

    private void reset() {
        this.animationPool = new AnimationPool();
        this.stackManager = new StackManager(this.animationPool);
        this.reloadButton = new ImageButton(10, 10, 25, 25, "./sprites/refreshing.png");
        this.elapsedTimeSeconds = 0;

        int startX = 100;
        int y = 250;
        int spacing = 90;

        for (int i = 1; i <= 7; i++) {
            try {
                this.stackManager.addSimpleStack(new Point(startX + (i - 1) * spacing, y), i);
            } catch (CardGeneratorEmptyException e) {
                throw new RuntimeException(e);
            }
        }

        int depositStartX = 400;
        int depositY = 65;
        int depositSpacing = 90;

        for (int i = 0; i < 4; i++) {
            this.stackManager.addDepositStack(new Point(depositStartX + i * depositSpacing, depositY));
        }

        try {
            this.stackManager.addRestStack(new Point(startX, depositY));
        } catch (CardGeneratorEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    public void loop(double elapsed) {
        this.update(elapsed);
        this.render(elapsed);
    }

    public void update(double elapsed) {
        this.elapsedTimeSeconds += elapsed;
        this.animationPool.update(elapsed);
    }

    public void mousePress(double x, double y) {
        this.stackManager.pick(x, y);
    }

    public void mouseReleased(double x, double y) {
        this.stackManager.drop(x, y);
    }

    public void mouseDragged(double x, double y) {
        this.stackManager.drag(x, y);
    }

    public void mouseMoved(double x, double y) {
        this.stackManager.drag(x, y);
    }

    public void mouseClicked(double x, double y) {
        if (reloadButton.isOver(new Point(x, y))) {
            this.reset();
        }
    }

    public void render(double elapsed) {
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        ctx.setFill(BgColor);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        this.reloadButton.draw(ctx);
        this.stackManager.draw(ctx);

        if (clockIcon != null) {
            double posX = 10;
            double posY = canvas.getHeight() - 30;

            ctx.drawImage(clockIcon, posX, posY, ClockIconSize, ClockIconSize);

            ctx.setFill(Color.WHITE);
            int fontSize = 18;
            ctx.setFont(javafx.scene.text.Font.font("Arial", fontSize));
            String timeText = formatTime(elapsedTimeSeconds);

            double textPosY = posY + (ClockIconSize / 2) + (fontSize * 0.35);
            ctx.fillText(timeText, posX + ClockIconSize + 8, textPosY);
        }
    }

    private String formatTime(double totalSeconds) {
        int minutes = (int)(totalSeconds / 60);
        int seconds = (int)(totalSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
