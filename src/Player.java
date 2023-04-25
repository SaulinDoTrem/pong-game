import java.awt.*;

public class Player {
    private int x;
    private int y;
    private int width;
    private int height;

    public Player(int x, int y, int width, int height) {
        this.setHeight(height);
        this.setWidth(width);
        this.setX(x);
        this.setY(y);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void tick() {

    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.fillRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
    }

}
