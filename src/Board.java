import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Board extends KeyPanel {
    private final int SQUARE_SIZE = 50;
    private final int LENGTH = 500;
    // private final int HEIGHT = 500;
    private Direction dir, lastPressed;
    private double x, y;
    private Timer t, ani;
    private int aniRepeats;
    private int animationTime, drawDelay;
    private boolean[] keys;
    private boolean[][] stepped;

    public Board() {
        dir = Direction.NEUTRAL;
        lastPressed = Direction.NEUTRAL;
        x = 0;
        y = 0;
        animationTime = 300;
        drawDelay = 10;
        t = new Timer(10, e -> checkKey());
        ani = new Timer(drawDelay, e -> moveCircle());
        t.setInitialDelay(0);
        aniRepeats = 0;
        keys = new boolean[4];
        stepped = new boolean[LENGTH / SQUARE_SIZE][LENGTH / SQUARE_SIZE];
        t.start();
    }

    private void checkKey() {
        dir = getKeyPressed();
        if (dir != Direction.NEUTRAL) {
            t.stop();
//            prevX = x;
//            prevY = y;
            update();
        }
    }
    private void update() {
        aniRepeats = animationTime / drawDelay;
        stepped[(int)x][(int)y] = true;
        ani.start();
    }

    private void moveCircle() {
        double movement = (double) drawDelay / animationTime;
        switch (dir) {
            case UP:
                y = Math.max(y-movement, 0);
                break;
            case DOWN:
                y = Math.min(y+movement, LENGTH / SQUARE_SIZE - 1);
                break;
            case LEFT:
                x = Math.max(x-movement, 0);
                break;
            case RIGHT:
                x = Math.min(x+movement, LENGTH / SQUARE_SIZE - 1);
                break;
        }
        aniRepeats--;
        if (aniRepeats == 0) {
            ani.stop();
            t.restart();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = SQUARE_SIZE; i < LENGTH; i += SQUARE_SIZE) {
            g.drawLine(0, i, LENGTH, i);
            g.drawLine(i, 0, i, LENGTH);
        }
        g.setColor(Color.BLUE);
        for (int i = 0; i < LENGTH / SQUARE_SIZE; i++) {
            for (int j = 0; j < LENGTH / SQUARE_SIZE; j++) {
                if (stepped[i][j])
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        g.setColor(Color.BLACK);
        int cX = (int) (x * SQUARE_SIZE + SQUARE_SIZE / 4);
        int cY = (int) (y * SQUARE_SIZE + SQUARE_SIZE / 4);
        g.drawOval(cX, cY, 20, 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LENGTH, LENGTH);
    }

    @Override
    public int getWidth() {
        return LENGTH;
    }

    @Override
    public int getHeight() {
        return LENGTH;
    }

    private Direction getKeyPressed() {
        if (lastPressed != Direction.NEUTRAL)
            return lastPressed;
        if (keys[0])
            return Direction.RIGHT;
        if (keys[1])
            return Direction.LEFT;
        if (keys[2])
            return Direction.UP;
        if (keys[3])
            return Direction.DOWN;

        return Direction.NEUTRAL;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                keys[0] = true;
                lastPressed = Direction.RIGHT;
                break;
            case KeyEvent.VK_LEFT:
                keys[1] = true;
                lastPressed = Direction.LEFT;
                break;
            case KeyEvent.VK_UP:
                keys[2] = true;
                lastPressed = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                keys[3] = true;
                lastPressed = Direction.DOWN;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                keys[0] = false;
                if (lastPressed == Direction.RIGHT)
                    lastPressed = Direction.NEUTRAL;
                break;
            case KeyEvent.VK_LEFT:
                keys[1] = false;
                if (lastPressed == Direction.LEFT)
                    lastPressed = Direction.NEUTRAL;
                break;
            case KeyEvent.VK_UP:
                keys[2] = false;
                if (lastPressed == Direction.UP)
                    lastPressed = Direction.NEUTRAL;
                break;
            case KeyEvent.VK_DOWN:
                keys[3] = false;
                if (lastPressed == Direction.DOWN)
                    lastPressed = Direction.NEUTRAL;
                break;
        }
    }
}
