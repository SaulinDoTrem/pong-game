import java.awt.*;

public class Player {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isGoingRight;
    private boolean isGoingLeft;
    private int speed;

    public Player(int x, int y, int width, int height, int speed) {
        this.setHeight(height);
        this.setWidth(width);
        this.setX(x);
        this.setY(y);
        this.setSpeed(speed);
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

    public void tick() {
        if(this.isGoingRight())
            this.setX(this.getX()+this.getSpeed());

        if(this.isGoingLeft())
            this.setX(this.getX()-this.getSpeed());

        if(this.getX()+this.getWidth() > Game.WIDTH*Game.SCALE)
            this.setX(Game.WIDTH*Game.SCALE - this.getWidth());

        if(this.getX() < 0)
            this.setX(0);
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.fillRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
    }

}
