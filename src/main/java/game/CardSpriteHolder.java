package game;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CardSpriteHolder extends JPanel {
    private Image cardImage;

    public CardSpriteHolder() {
        setBackground(new Color(0, 128, 0)); // verde de fundo
        loadImage();
    }

    private void loadImage() {
        // Carrega a imagem do resources/sprites/playingCards.png usando classloader
        URL imageUrl = getClass().getClassLoader().getResource("sprites/playingCards.png");
        if (imageUrl == null) {
            System.err.println("Erro: arquivo sprites/playingCards.png não encontrado no classpath!");
        } else {
            cardImage = new ImageIcon(imageUrl).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cardImage != null) {
            g.drawImage(cardImage, 100, 100, this);
        } else {
            g.setColor(Color.RED);
            g.drawString("Imagem não carregada!", 100, 100);
        }
    }
}
