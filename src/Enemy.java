import java.awt.*;
import java.util.Random;

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
        //Movimentação do adversário atrás da bola
        this.setX(this.getX() + ((Game.ball.getX() - this.getX() - 10) * 0.15));

        //Teste de colisão com a parede
        if(this.getX()+super.getWidth() >= Game.WIDTH*Game.SCALE)
            this.setX(Game.WIDTH*Game.SCALE - super.getWidth());
        if(this.getX() < 0)
            this.setX(0);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.fillRect((int)this.getX(), (int)this.getY(), super.getWidth(), super.getHeight());
    }

}
