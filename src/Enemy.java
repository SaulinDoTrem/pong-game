import java.awt.*;

public class Enemy extends Sprite{

    private double x;
    private double y;

    public Enemy(int width, int height, Color color, double x, double y) {
        super(width, height, color);
        this.setX(x);
        this.setY(y);
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(super.getColor());
        graphics.fillRect((int)this.getX(), (int)this.getY(), super.getWidth(), super.getHeight());
    }

}
