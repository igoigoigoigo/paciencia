package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.Point;

public class ImageButton {
    private double width;
    private double height;
    private String imageFilename;

    private Point position;
    private Image image;

    public ImageButton(double x, double y, double width, double height, String imageFilename) {
        this.position = new Point(x, y);
        this.width = width;
        this.height = height;
        this.imageFilename = imageFilename;

        try {
            FileInputStream stream = new FileInputStream(imageFilename);
            this.image = new Image(stream);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error opening image file: " + fnfe.getMessage());
        }
    }

    public boolean isOver(Point p) {
        return p.getX() >= this.position.getX() && p.getY() >= this.position.getY()
                && p.getX() <= this.position.getX() + width && p.getY() <= this.position.getY() + height;
    }

    public void draw(GraphicsContext ctx) {
        if (this.image != null) {
            ctx.drawImage(this.image, this.position.getX(), this.position.getY(), width, height);
        }
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
