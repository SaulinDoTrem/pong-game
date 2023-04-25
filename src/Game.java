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
    private static int PLAYER_WIDTH = 120;
    private static int PLAYER_HEIGHT = 10;
    private static int PLAYER_SPEED = 5;
    public static boolean isRunning;
    private Thread thread;
    private Player player = new Player(((WIDTH*SCALE)/2)-PLAYER_WIDTH/2, HEIGHT*SCALE-PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED);
    private BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

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
        this.player.tick();
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = this.layer.getGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0,0,this.WIDTH, this.HEIGHT);


        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(this.layer, 0, 0, this.WIDTH*this.SCALE, this.HEIGHT*this.SCALE, null);


        this.player.render(graphics);

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
            case KeyEvent.VK_LEFT -> {
                this.player.setGoingLeft(true);
            }
            case KeyEvent.VK_RIGHT -> {
                this.player.setGoingRight(true);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                this.player.setGoingLeft(true);
            }
            case KeyEvent.VK_RIGHT -> {
                this.player.setGoingRight(true);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                this.player.setGoingLeft(false);
            }
            case KeyEvent.VK_RIGHT -> {
                this.player.setGoingRight(false);
            }
        }
    }
}