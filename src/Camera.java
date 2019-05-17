import javax.swing.*;

public class Camera {
    private double camX;
    private double camY;
    private int maxX;
    private int maxY;

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    public Camera(JPanel panel) {
        SCREEN_WIDTH = panel.getWidth();
        SCREEN_HEIGHT = panel.getHeight();
    }

    protected void setMax(int x, int y) {
        maxX = x - SCREEN_WIDTH;
        maxY = y - SCREEN_HEIGHT;
        camX = 0;                      //camX and camY are the (0,0) coordinates for the screen
        camY = 0;
    }

    protected void trackPlayer(double x, double y) {
        camX = x - SCREEN_WIDTH / 2;
        camY = y - SCREEN_HEIGHT / 2;

        if (camX > maxX)
            camX = maxX;
        else if (camX < 0)
            camX = 0;

        if (camY > maxY)
            camY = maxY;
        else if (camY < 0)
            camY = 0;
    }

    public boolean isMaxX() {
        return camX == maxX;
    }

    public boolean isMinX() {
        return camX == 0;
    }

    public boolean isMaxY() {
        return camY == maxY;
    }

    public boolean isMinY() {
        return camY == 0;
    }

    protected boolean isInView(int minX, int minY, int maxX, int maxY) {
        return (camX < maxX || minX < camX + SCREEN_WIDTH) && (camY < maxY || minY < camY + SCREEN_HEIGHT);
    }

    public double getCamX() {
        return camX;
    }

    public double getCamY() {
        return camY;
    }
}
