import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class TPanel extends JPanel {

//    private int xPos;
//    private int yPos;
    private Set<Platform> platforms;
    private boolean[] keys;
    private boolean inMidair;
    private double time;
    private Timer yTimer;
    private int initialHeight;
    private Camera cam;
    private Square s;
    private final int FLOOR;

    private static final int REFRESH_INTERVAL = 10;
    private static final double TIME_PER_INT = 0.5;
    public static final int worldWidth = 1000;
    public static final int worldHeight = 1000;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int SWIDTH = 20;
    public static final int SHEIGHT = 20;


    public TPanel() {
        s = new Square(SWIDTH,SHEIGHT, WIDTH / 2,worldHeight - SHEIGHT);
        FLOOR = worldHeight - SHEIGHT;
        platforms = new HashSet<>();
        platforms.add(new Platform(200, 900, 50));
        platforms.add(new Platform(250, 800, 20));
        platforms.add(new Platform(100, 600, 30));
        platforms.add(new Platform(400, 550, 40));
        platforms.add(new Platform(350, 700, 10));
        keys = new boolean[3];
        inMidair = false;
        time = 0;
        yTimer = new Timer(REFRESH_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveY();
            }
        });
//        worldWidth = 2000;
//        worldHeight = 1000;

        cam = new Camera(this);
        cam.setMax(worldWidth, worldHeight);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void setX(int d) {
        s.setX(d);
    }

    private void setY(int h) {
        s.setY(FLOOR - h);
    }

    private void moveX(boolean dir) {
        setX(Physics.getX(dir, (int)Math.round(s.getX())));
        if (!inMidair && s.getY() != FLOOR && !onPlatform()) {
            inMidair = true;
            initialHeight = (int)Math.round(s.getY());
            Physics.setDY(0);
            yTimer.start();
        }
    }

    private boolean onPlatform() {
        for (Platform p : platforms) {
            if ((int)Math.round(s.getY()) == FLOOR - p.Y)
                return (s.getX() + SWIDTH) > p.X && s.getX() < (p.X + p.LENGTHX);
        }
        return false;
    }

    private void moveY() {
        time += TIME_PER_INT;

        if (checkCollision())
            return;

        if (Physics.getY(time, FLOOR - initialHeight) >= 0) {
            setY(Math.round((float) Physics.getY(time, FLOOR - initialHeight)));
        } else {
            setY(0);
            yTimer.stop();
            inMidair = false;
            time = 0;
        }
    }

    private boolean checkCollision() {
        for (Platform p : platforms) {
            if (Math.abs(s.getY() + SHEIGHT - p.Y) <= 12 && (s.getX() + SWIDTH) > p.X && s.getX() < (p.X + p.LENGTHX) && Physics.getDY(time) < 0) {
                setY(worldHeight - p.Y);
                yTimer.stop();
                inMidair = false;
                time = 0;
                return true;
            }
        }
        return false;
    }

    private void jump() {
        initialHeight = (int)Math.round(s.getY());
        Physics.setDY(40);
        yTimer.start();
    }

    public void update() {
        if (keys[0])
            moveX(true);
        if (keys[1])
            moveX(false);
        if (keys[2] && !inMidair) {
            inMidair = true;
            jump();
        }

        cam.trackPlayer(s.getX(), s.getY());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        drawSquare(g);
        drawSquare(g, s);
        drawPlatforms(g);
    }

    private void drawSquare(Graphics g, Square sq) {
        g.drawRect((int)Math.round(sq.getX() - cam.getCamX()), (int)Math.round(sq.getY() - cam.getCamY()), sq.getLength(), sq.getWidth());
    }
//    private void drawSquare(Graphics g) {
//        if ((cam.isMaxX() && cam.isMaxY()) || (cam.isMinX() && cam.isMinY()))
//            g.drawRect(cam.getCamX(), cam.getCamY(), SWIDTH, SHEIGHT);
//        else if ((cam.isMinX() && cam.isMaxY()) || (cam.isMaxX() && cam.isMinY()))
//            g.drawRect(xPos, yPos, SWIDTH, SHEIGHT);
//        else if (cam.isMaxX() || cam.isMinX())
//            g.drawRect(cam.getCamX(), HEIGHT / 2, SWIDTH, SHEIGHT);
//        else if (cam.isMaxY() || cam.isMinY())
//            g.drawRect(WIDTH / 2, cam.getCamY(), SWIDTH, SHEIGHT);
//        else
//            g.drawRect(WIDTH / 2, HEIGHT / 2, SWIDTH, SHEIGHT);
//    }

    private void drawPlatforms(Graphics g) {
        for (Platform p : platforms)
            // if (cam.isInView(p.X, p.Y, p.X + p.LENGTHX, p.Y))
                p.drawPlatform(g, cam);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            keys[0] = true;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            keys[1] = true;
        else
            keys[2] = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            keys[0] = false;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            keys[1] = false;
        else
            keys[2] = false;
    }
}