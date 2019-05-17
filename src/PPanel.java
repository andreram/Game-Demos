import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PPanel extends JPanel {
    private final int LENGTH = 500;
    private final int HEIGHT = 500;
    private Square s;
    private Platform p;
    private int vX = 2;
    private int vY = 3;
    private boolean isDrawing;
    private int wallX, wallY, dragX, dragY;

    public PPanel() {
        s = new Square(20,20,250,250);
        MouseInputAdapter e = new MouseInputHandler();
        this.addMouseListener(e);
        this.addMouseMotionListener(e);
        p = null;
        isDrawing = false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LENGTH, HEIGHT);
    }

    @Override
    public int getWidth() {
        return LENGTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public void update() {
        s.move(vX, vY);
        if (willCollide(false)) {
            vX = -vX;
            s.move(vX, 0);
        }

        if (willCollide(true)) {
            vY = -vY;
            s.move(0, vY);
        }

        if (p != null && collidingWithPlatform()) {
            vX = -vX;
            vY = p.perpSlope();
            p = null;
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect((int)s.getX(), (int)s.getY(), s.getLength(), s.getWidth());
        if (isDrawing)
            g.drawLine(wallX, wallY, dragX, dragY);

        if (p != null)
            p.drawPlatform(g);
    }

    private boolean willCollide(boolean checkingY) {
        if (checkingY) {
            return (s.getY() <= 0) || (s.getY() + s.getWidth() >= HEIGHT);
        }
        return (s.getX() <= 0) || (s.getX() + s.getLength() >= LENGTH);
    }

    private boolean collidingWithPlatform() {
        if (p.inXRange((int)(s.getX()), (int)(s.getX() + s.getLength()))) {
            int y = p.findY((int)s.getX());
            return (s.getY()) <= y && y <= (s.getY() + s.getWidth());
        }
        return false;
    }

    private class MouseInputHandler extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            isDrawing = true;
            wallX = e.getX();
            wallY = e.getY();
            dragX = wallX;
            dragY = wallY;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            p = new Platform(wallX, wallY, e.getX(), e.getY());
            isDrawing = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            dragX = e.getX();
            dragY = e.getY();
        }
    }
}
