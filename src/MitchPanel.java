import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MitchPanel extends JPanel {


    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private int worldWidth;
    private int worldHeight;
    private boolean[] keys;
    private Square s;
    private Camera cam;
    private Set<Platform> platforms;
    private Platform leftAndRight1;
    private Platform leftAndRight2;
    private Platform upAndDown1;
    private Platform upAndDown2;
    private ArrayList<Integer> dir;
    private int gameTime;

    public MitchPanel() {
        worldWidth = 1000;
        worldHeight = 1000;
        s = new Square(20, 20, 250, 250);
        keys = new boolean[4];
        cam = new Camera(this);
        cam.setMax(worldWidth, worldHeight);
        gameTime = 0;
        platforms = new HashSet<>();
//        platforms.add(new Platform(250, 400, 30, 0));
//        platforms.add(new Platform(100, 200, 40, 0));
//        platforms.add(new Platform(350, 400, 30, 30));
//        platforms.add(new Platform(300, 200, 0, 40));
//        leftAndRight1 = new Platform(200, 700, 40, 0);
//        platforms.add(leftAndRight1);
//        leftAndRight2 = new Platform(200, 800, 0, 40);
//        platforms.add(leftAndRight2);
//        upAndDown1 = new Platform(700, 700, 0, 40);
//        upAndDown2 = new Platform(800, 700, 30, 0);
        platforms.add(upAndDown1);
        platforms.add(upAndDown2);
        dir = new ArrayList<>();
        dir.add(0);//.get(0) for right, 1 for left, 2 for up, 3 for down
        dir.add(0);
        dir.add(0);
        dir.add(0);
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

        if(dir.get(0) > 49 || dir.get(1) > 49 || dir.get(2) > 49 || dir.get(3) > 49)
            dir.set(getMax(dir), 19);


        if(keys[0])
            dir.set(0, dir.get(0) + 1);

        if(keys[1])
            dir.set(1, dir.get(1) + 1);

        if(keys[2])
            dir.set(2, dir.get(2) + 1);

        if(keys[3])
            dir.set(3, dir.get(3) + 1);


        if(!keys[0]) {
            if(dir.get(0) > 10)
                dir.set(0, dir.get(0) - 5);
            else
                dir.set(0, 0);
        }
        if(!keys[1]) {
            if(dir.get(1) > 10)
                dir.set(1, dir.get(1) - 10);
            else
                dir.set(1, 0);
        }
        if(!keys[2]) {
            if(dir.get(2) > 10)
                dir.set(2, dir.get(2) - 5);
            else
                dir.set(2, 0);
        }
        if(!keys[3]) {
            if(dir.get(3) > 10)
                dir.set(3, dir.get(3) - 10);
            else
                dir.set(3, 0);
        }

        accel();

        this.leftAndRight1.changeX(3*periodic(2,0));
        this.leftAndRight2.changeX(3*periodic(3,0));
        this.upAndDown1.changeY(periodic(1,0));
        this.upAndDown2.changeY(periodic(1,10));


        if (!checkInboundX())
            s.setX(Math.abs(0 - s.getX()) < Math.abs(worldWidth - s.getX()) ? 0 : worldWidth - s.getLength());

        if (!checkInboundY())
            s.setY(Math.abs(0 - s.getY()) < Math.abs(worldHeight - s.getY()) ? 0 : worldHeight - s.getWidth());

        cam.trackPlayer(s.getX(), s.getY());

        repaint();
        this.gameTime += Main.REFRESH_RATE;
    }

    public int periodic(int freq, int offset) {//freq should be a factor of 1000 / refreshRate
        Double a = Math.floor((this.gameTime+offset*Main.REFRESH_RATE)*freq/1000);
        return Math.floorMod(a.intValue(), 2)*2-1;
    }

    public void accel() {
        s.accelerate(dir.get(0)-dir.get(1), dir.get(3)-dir.get(2));
    }

    public int getMax(ArrayList<Integer> a) {
        int out = 0;
        int max = a.get(0);
        for(int c = 0; c < a.size(); c++) {
            if(a.get(c) > max) {
                max = a.get(c);
                out = c;
            }
        }
        return out;
    }

    private boolean checkInboundX() {
        return 0 < s.getX() && (s.getX() + s.getLength() < worldWidth);
    }

    private boolean checkInboundY() {
        return 0 < s.getY() && (s.getY() + s.getWidth() < worldHeight);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSquare(g);
        drawPlatforms(g);
    }

    private void drawSquare(Graphics g) {
        //g.drawRect(s.getX() - cam.getCamX(), s.getY() - cam.getCamY(), s.getLength(), s.getWidth());
    }

    private void drawPlatforms(Graphics g) {
        for (Platform p : platforms)
//            if (cam.isInView(p.X, p.Y, p.X + p.LENGTH, p.Y))
            p.drawPlatform(g, cam);
    }

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
