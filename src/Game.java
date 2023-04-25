import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 160;
    public static int HEIGHT = 120;
    public static int SCALE = 3;
    public static int PLAYER_POINTS = 0;
    public static int ENEMY_POINTS = 0;
    private static final int PLAYER_WIDTH = 40*SCALE;
    private static final int PLAYER_HEIGHT = 5*SCALE;
    private static final int PLAYER_SPEED = 4;
    private static final int PLAYER_X = ((WIDTH*SCALE)/2)-PLAYER_WIDTH/2;
    private static final int PLAYER_Y = HEIGHT*SCALE-PLAYER_HEIGHT;
    private static final int BALL_DIMENSION = 6*SCALE;
    private static final int BALL_X = (WIDTH*SCALE/2)-(BALL_DIMENSION/2);
    private static final int BALL_Y = (HEIGHT*SCALE/2)-(BALL_DIMENSION/2);
    private static final double BALL_SPEED = 1.6;
    private static final Color BALL_COLOR = Color.yellow;
    private static final Color ENEMY_COLOR = Color.red;
    private static final Color PLAYER_COLOR = Color.blue;
    public static Player player;
    public static Ball ball;
    public static Enemy enemy;
    public static Sprite[] sprites;
    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.addKeyListener(this);
        player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_COLOR);
        enemy = new Enemy(PLAYER_WIDTH, PLAYER_HEIGHT, ENEMY_COLOR, PLAYER_X, 0);
        ball = new Ball(BALL_DIMENSION, BALL_COLOR, BALL_X, BALL_Y, BALL_SPEED);
        sprites = new Sprite[]{player, enemy, ball};
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initFrame();

        new Thread(game).start();
    }

    public void initFrame() {
        JFrame frame = new JFrame("Pong Game");

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void tick() {
        for (Sprite sprite: sprites) {
            sprite.tick();
        }
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = this.layer.getGraphics();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(this.layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);


        for (Sprite sprite: sprites) {
            sprite.render(graphics);
        }

        this.renderPoints(graphics);

        bufferStrategy.show();
    }

    public void renderPoints(Graphics graphics) {
        int pointsY = HEIGHT*SCALE/2;
        int pointsX = 16;

        graphics.setFont(new Font("Arial", Font.BOLD, 16));
        graphics.setColor(Color.RED);
        graphics.drawString(""+ ENEMY_POINTS, pointsX, pointsY);

        pointsX+=10;

        graphics.setFont(new Font("Arial", Font.BOLD, 16));
        graphics.setColor(Color.white);
        graphics.drawString(" X ", pointsX, pointsY);

        pointsX+=20;

        graphics.setFont(new Font("Arial", Font.BOLD, 18));
        graphics.setColor(Color.blue);
        graphics.drawString(""+PLAYER_POINTS, pointsX, pointsY);
    }

    @Override
    public void run() {
        while(true) {
            this.tick();
            this.render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT ->
                player.setGoingLeft(true);
            case KeyEvent.VK_RIGHT ->
                player.setGoingRight(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT ->
                player.setGoingLeft(true);
            case KeyEvent.VK_RIGHT ->
                player.setGoingRight(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT ->
                player.setGoingLeft(false);
            case KeyEvent.VK_RIGHT ->
                player.setGoingRight(false);
        }
    }
}