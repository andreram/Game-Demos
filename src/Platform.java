import java.awt.*;

public class Platform {
    public int X;
    public int Y;
    public final int LENGTHX;
    public final int LENGTHY;

    public Platform(int x, int y, int length) {
        X = x;
        Y = y;
        LENGTHX = length;
        LENGTHY = 0;
    }

//    public Platform(int x, int y, int lengthx, int lengthy) {
//        X = x;
//        Y = y;
//        LENGTHX = lengthx;
//        LENGTHY = lengthy;
//    }

    public Platform(int x1, int y1, int x2, int y2) {
        X = x1;
        Y = y1;
        LENGTHX = x2 - x1;
        LENGTHY = y2 - y1;
    }

    public void changeX(int d) {X += d;}
    public void changeY(int d) {Y += d;}

    public boolean inXRange(int low, int high) {
        int x2 = X + LENGTHX;
        if (x2 < X) {
            return (x2 <= low && low <= X) || (x2 <= high && high <= X);
        }
        return (X <= low && low <= x2) || (X <= high && high <= x2);
    }

    public int findY(int x) {
        double b = Y - (double)LENGTHY / LENGTHX * X;
        return (int)((double)LENGTHY / LENGTHX * x + b);
    }

    public int perpSlope() {
        return -LENGTHX / LENGTHY;
    }

    public void drawPlatform(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.drawLine(X, Y, X + LENGTHX, Y + LENGTHY);
        g.setColor(c);
    }

    public void drawPlatform(Graphics g, Camera c) {
        g.drawLine((int)(X - c.getCamX()), (int)(Y - c.getCamY()), (int)(X - c.getCamX() + LENGTHX), (int)(Y + LENGTHY - c.getCamY()));
    }

}
