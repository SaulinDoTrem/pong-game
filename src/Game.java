import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 240;
    public static int HEIGHT = 120;
    public static int SCALE = 3;
    private static final int PLAYER_WIDTH = 120;
    private static final int PLAYER_HEIGHT = 10;
    private static final int PLAYER_SPEED = 5;
    private static final int PLAYER_X = ((WIDTH*SCALE)/2)-PLAYER_WIDTH/2;
    private static final int PLAYER_Y = HEIGHT*SCALE-PLAYER_HEIGHT;
    private static final int BALL_DIMENSION = 6;
    private static final int BALL_X = (WIDTH*SCALE/2)-(BALL_DIMENSION/2);
    private static final int BALL_Y = (HEIGHT*SCALE/2)-(BALL_DIMENSION/2);
    private static final double BALL_SPEED = 1.6;
    private static final Color BALL_COLOR = Color.yellow;
    private static final Color ENEMY_COLOR = Color.red;
    private static final Color PLAYER_COLOR = Color.blue;
    public static boolean isRunning;
    private Thread thread;
    public static Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_COLOR);
    public static Ball ball = new Ball(BALL_DIMENSION, BALL_COLOR, BALL_X, BALL_Y, BALL_SPEED);
    public static Enemy enemy = new Enemy(PLAYER_WIDTH, PLAYER_HEIGHT, ENEMY_COLOR, PLAYER_X, 0);
    public Sprite[] sprites = {player, enemy, ball};
    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.addKeyListener(this);
        this.initFrame();
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.start();
    }

    public synchronized void start() {
        this.thread = new Thread(this);
        isRunning = true;
        this.thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        for (Sprite sprite: this.sprites) {
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
        graphics.setColor(Color.black);
        graphics.fillRect(0,0,WIDTH, HEIGHT);


        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(this.layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);



        for (Sprite sprite: this.sprites) {
            sprite.render(graphics);
        }

        bufferStrategy.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = Math.pow(10, 9) / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                this.tick();
                this.render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }

        }

        this.stop();
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