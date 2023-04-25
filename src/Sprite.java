import java.awt.*;

public abstract class Sprite {
    protected int width;
    protected int height;
    protected Color color;

    public Sprite(int width, int height, Color color) {
        this.setHeight(height);
        this.setWidth(width);
        this.setColor(color);
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

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }


    public abstract void tick();
    public void render(Graphics graphics) {
        graphics.setColor(this.getColor());
    }
}
