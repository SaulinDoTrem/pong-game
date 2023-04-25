import java.awt.*;
import java.util.Random;

public class Ball extends Sprite{
    private double x;
    private double y;
    private double directionX;
    private double directionY;
    public double speed;

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionX() {
        return this.directionX;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public double getDirectionY() {
        return this.directionY;
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

    public Ball(int dimension, Color color, double x, double y, double speed) {
        super(dimension, dimension, color);
        this.setX(x);
        this.setY(y);
        this.setSpeed(speed);

        this.setBallAngle();
    }

    public void setBallAngle() {
        int angle = new Random().nextInt(120 - 45) + 45;

        this.setDirectionX(-Math.cos(Math.toRadians(angle)));
        this.setDirectionY(Math.sin(Math.toRadians(angle)));
    }

    @Override
    public void tick() {
        //Movimentação da Bolinha
        this.setX(this.getX()+(this.getDirectionX()*this.getSpeed()));
        this.setY(this.getY()+(this.getDirectionY()*this.getSpeed()));

        double position = this.getX()+(this.getDirectionX()*this.getSpeed());

        //Teste de colisão com a parede
        if(position+super.getWidth() >= Game.WIDTH*Game.SCALE || position < 0) {
            increaseBallSpeedWhenIntersects(0.2);
            this.setDirectionX(this.getDirectionX()*-1);
        }

        //Pontuação conforme a bola passa
        if(this.getY()+this.getHeight() >= Game.HEIGHT*Game.SCALE) {
            Game.ENEMY_POINTS++;
            new Game();
            return;
        }
        if(this.getY() < 0) {
            Game.PLAYER_POINTS++;
            new Game();
            return;
        }

        //Simula a bola
        Rectangle ball = new Rectangle(
                (int)(this.getX()+(this.getDirectionX()*this.getSpeed())),
                (int)(this.getY()+(this.getDirectionY()*this.getSpeed())),
                this.getWidth(),
                this.getHeight()
        );

        //Simula o jogador
        Rectangle player = new Rectangle(
                Game.player.getX(),
                Game.player.getY(),
                Game.player.getWidth(),
                Game.player.getHeight()
        );

        //Simula o adversário
        Rectangle enemy = new Rectangle(
                (int)Game.enemy.getX(),
                (int)Game.enemy.getY(),
                Game.enemy.getWidth(),
                Game.enemy.getHeight()
        );

        //Testa colisão com o jogador e o adversário
        if(ball.intersects(player)) {
            this.increaseBallSpeedWhenIntersects(0.4);
            this.setBallAngle();
            if(this.getDirectionY() > 0)
                this.setDirectionY(this.getDirectionY()*-1);
        }
        if(ball.intersects(enemy)) {
            this.increaseBallSpeedWhenIntersects(0.4);
            this.setBallAngle();
            if(this.getDirectionY() < 0)
                this.setDirectionY(this.getDirectionY()*-1);
        }

    }

    public void increaseBallSpeedWhenIntersects(double speed) {
        this.setSpeed(this.getSpeed()+speed);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.fillRect((int)this.getX(), (int)this.getY(), super.getWidth(), super.getHeight());
    }
}
