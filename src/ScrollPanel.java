import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class ScrollPanel extends ImagePanel {

    private final double V = 2;
    private int worldWidth;
    private int worldHeight;
    private boolean[] keys;
    private Square s;
    private Camera cam;
    private Set<Platform> platforms;
    private Homer homer;
    private boolean flip;

    public ScrollPanel() {
        worldWidth = 1000;
        worldHeight = 1000;
        s = new Square(100, 100, 250, 250);
        keys = new boolean[4];
        cam = new Camera(this);
        cam.setMax(worldWidth, worldHeight);
        platforms = new HashSet<>();
        platforms.add(new Platform(750, 800, 50));
        platforms.add(new Platform(250, 400, 30));
        platforms.add(new Platform(100, 200, 40));
//        platforms.add(new Platform(300, 600, 0, 50));
//        platforms.add(new Platform(400, 500, 0, 50));
//        platforms.add(new Platform(350, 400, 30, 30));
        platforms.add(new MovingPlatform(800, 50, 40, 0, 500, 2, 0));
        platforms.add(new MovingPlatform(750, 150, 0, 50, 1200, 0, 1));
        platforms.add(new MovingPlatform(650, 300, 30, 0, 800, 0, 2));
        platforms.add(new MovingPlatform(600, 400, 0, 35, 1500, 1, 0));
        // homer = new Homer(28, 28, 250, 250);
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

    public void update() {
        if (keys[0]) {
            s.move(V, 0);
            if (isColliding(true))
                s.move(-V, 0);
        }
        if (keys[1]) {
            s.move(-V, 0);
            if (isColliding(true))
                s.move(V, 0);
        }
        if (keys[2]) {
            s.move(0, -V);
            if (isColliding(false))
                s.move(0, V);
        }
        if (keys[3]) {
            s.move(0, V);
            if (isColliding(false))
                s.move(0, -V);
        }

        isCollidingWithMovingPlatform();

        if (!checkInboundX())
            s.setX(Math.abs(0 - s.getX()) < Math.abs(worldWidth - s.getX()) ? 0 : worldWidth - s.getLength());

        if (!checkInboundY())
            s.setY(Math.abs(0 - s.getY()) < Math.abs(worldHeight - s.getY()) ? 0 : worldHeight - s.getWidth());

        cam.trackPlayer(s.getX(), s.getY());
        // homer.home(s);
        setImgPos((int)cam.getCamX(), (int)cam.getCamY());
        if ((flipOnRight && keys[0]) || (!flipOnRight && keys[1]))
            flip = true;
        else if ((flipOnRight && keys[1]) || (!flipOnRight && keys[0]))
            flip = false;

        repaint();
    }

    private boolean checkInboundX() {
        return 0 < s.getX() && (s.getX() + s.getLength() < worldWidth);
    }

    private boolean checkInboundY() {
        return 0 < s.getY() && (s.getY() + s.getWidth() < worldHeight);
    }

    private boolean isColliding(boolean movingX) {
        for (Platform p : platforms) {
            if(checkCollision(p, movingX)) {
                return true;
            }
        }
        return false;
    }

    private void isCollidingWithMovingPlatform() {
        for (Platform p : platforms) {
            if (p instanceof MovingPlatform) {
                MovingPlatform pl = (MovingPlatform) p;
                pl.move(pl.getDx(), pl.getDy());
                while (checkCollision(pl, true) || checkCollision(pl, false)) {
                    s.move(pl.getDx(), pl.getDy());
                }
                pl.move(-pl.getDx(), -pl.getDy());
            }
        }
    }

    private boolean checkCollision(Platform p, boolean movingX) {
        if (movingX) {
            return willCollide(p, true) && ((s.getX() <= p.X && p.X <= s.getX() + s.getLength()) || (s.getX() <= p.X + p.LENGTHX && p.X + p.LENGTHX <= s.getX() + s.getLength()));
        } else {
            return willCollide(p, false) && ((s.getY() <= p.Y && p.Y <= s.getY() + s.getWidth()) || (s.getY() <= p.Y + p.LENGTHY && p.Y + p.LENGTHY <= s.getY() + s.getWidth()));
        }
    }

    private boolean willCollide(Platform p, boolean checkingY) {
        if (checkingY) {
            return (s.getY() >= p.Y && s.getY() <= p.Y + p.LENGTHY) || (s.getY() <= p.Y && s.getY() + s.getWidth() >= p.Y);
        }
        return (s.getX() >= p.X && s.getX() <= p.X + p.LENGTHX) || (s.getX() <= p.X && s.getX() + s.getLength() >= p.X);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSquare(g, s);
        drawPlatforms(g);
        // drawSquare(g, homer);
    }

    private void drawSquare(Graphics g, Square sq) {
        // g.drawRect((int)Math.round(sq.getX() - cam.getCamX()), (int)Math.round(sq.getY() - cam.getCamY()), sq.getLength(), sq.getWidth());
        if (!flip)
            g.drawImage(sprite,(int)Math.round(sq.getX() - cam.getCamX()), (int)Math.round(sq.getY() - cam.getCamY()),
                    (int)Math.round(sq.getX() - cam.getCamX()) + sq.getLength(),(int)Math.round(sq.getY() - cam.getCamY()) + sq.getWidth(),
                    frameX + 115, 0, frameX, 137,null);
            // g.drawImage(sprite,(int)Math.round(sq.getX() - cam.getCamX()) + sq.getLength(), (int)Math.round(sq.getY() - cam.getCamY()), -sq.getLength(), sq.getWidth(), null);
        else
            g.drawImage(sprite,(int)Math.round(sq.getX() - cam.getCamX()), (int)Math.round(sq.getY() - cam.getCamY()),
                    (int)Math.round(sq.getX() - cam.getCamX()) + sq.getLength(),(int)Math.round(sq.getY() - cam.getCamY()) + sq.getWidth(),
                    frameX, 0, frameX + 115, 137,null);
            // g.drawImage(sprite,(int)Math.round(sq.getX() - cam.getCamX()), (int)Math.round(sq.getY() - cam.getCamY()), sq.getLength(), sq.getWidth(),null);
    }

    private void drawPlatforms(Graphics g) {
        for (Platform p : platforms)
//            if (cam.isInView(p.X, p.Y, p.X + p.LENGTH, p.Y))
            p.drawPlatform(g, cam);
    }

//    private void drawHomer(Graphics g) {
//        g.drawRect((int)homer.getX(), (int)homer.getY(), homer.getLength(), homer.getWidth());
//    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            keys[0] = true;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            keys[1] = true;
        else if (e.getKeyCode() == KeyEvent.VK_UP)
            keys[2] = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            keys[3] = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            keys[0] = false;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            keys[1] = false;
        else if (e.getKeyCode() == KeyEvent.VK_UP)
            keys[2] = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            keys[3] = false;
    }


}
