public class Physics {
    private static final double G = -4;
    private static final int dX = 5;
    private static double dY;

    public static double getY(double time, int initialHeight) {
        return (dY*time + (G*time*time)/2) + initialHeight;
    }

    public static double getDY(double time) {
        return (G*time + dY);
    }

    public static void setDY(double speed) {
        dY = speed;
    }

    public static int getX(boolean dir, int x) {
        if(dir)
            return Math.min(x + dX, TPanel.worldWidth - TPanel.SWIDTH);
        else
            return Math.max(x - dX, 0);
    }
}
