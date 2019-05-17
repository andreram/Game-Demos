import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image img;
    private int imgX, imgY;
    protected final int WIDTH = 500;
    protected final int HEIGHT = 500;
    protected Image sprite;
    protected int frameX = 0;
    protected final boolean flipOnRight = true;

    public ImagePanel() {
        this.img = new ImageIcon("banana.png").getImage();
        this.sprite = new ImageIcon("sample.png").getImage();
        imgX = 0;
        imgY = 0;
        new Timer(120, e -> animate()).start();
    }

    private void animate() {
        frameX = (frameX + 115) % 920;
    }

    protected void setImgPos(int x, int y) {
        imgX = x;
        imgY = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0, WIDTH, HEIGHT, imgX, imgY,imgX + WIDTH,imgY + HEIGHT,null);
    }
}
