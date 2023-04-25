import java.awt.*;

public class Player extends Sprite{
    private int x;
    private int y;
    private boolean isGoingRight;
    private boolean isGoingLeft;
    private int speed;

    public Player(int x, int y, int width, int height, int speed, Color color) {
        super(width, height, color);
        this.setX(x);
        this.setY(y);
        this.setSpeed(speed);
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

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setGoingLeft(boolean goingLeft) {
        this.isGoingLeft = goingLeft;
    }

    public boolean isGoingLeft() {
        return this.isGoingLeft;
    }

    public void setGoingRight(boolean goingRight) {
        this.isGoingRight = goingRight;
    }

    public boolean isGoingRight() {
        return this.isGoingRight;
    }

    @Override
    public void tick() {
        if(this.isGoingRight())
            this.setX(this.getX()+this.getSpeed());

        if(this.isGoingLeft())
            this.setX(this.getX()-this.getSpeed());

        if(this.getX()+super.getWidth() > Game.WIDTH*Game.SCALE)
            this.setX(Game.WIDTH*Game.SCALE - super.getWidth());

        if(this.getX() < 0)
            this.setX(0);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(super.getColor());
        graphics.fillRect(this.getX(), this.getY(), super.getWidth(), super.getHeight());
    }

}
