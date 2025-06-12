package util;

public class Rectangle {
    private final int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Point p) {
        return p.getX() >= x && p.getX() <= x + width &&
                p.getY() >= y && p.getY() <= y + height;
    }
}
