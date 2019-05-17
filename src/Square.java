public class Square {

    private int length;
    private int width;
    private double x;
    private double y;

    private double dx_dt;
    private double dy_dt;


    public Square(int length, int width, int x, int y) {
        this.length = length;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public int getLength() {
        return  length;
    }

    public int getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void accelerate(double dX, double dY) {
        this.dx_dt = 4*Math.atan(dX*Math.PI/100);
        this.dy_dt = 4*Math.atan(dY*Math.PI/100);
        move(this.dx_dt,this.dy_dt);
    }

    public void setXVelo(double dX) {
        dx_dt = dX;
    }

    public void setYVelo(double dY) {
        dy_dt = dY;
    }

    public double getXVelo() {return this.dx_dt;}
    public double getYVelo() {return this.dy_dt;}

}
