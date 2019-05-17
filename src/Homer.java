public class Homer extends Square {

    private final int SPEED = 1;

    public Homer(int length, int width, int x, int y) {
        super(length, width, x, y);
    }

    public void home(Square s) {
        if (!(s.getX() == this.getX() && s.getY() == this.getY())) {
            if (s.getY() == this.getY()) {
                double dx = s.getX() - this.getX();
                move(SPEED * (dx / Math.abs(dx)), 0);
            } else if (s.getX() == this.getX()) {
                double dy = s.getY() - this.getY();
                move(0, SPEED * (dy / Math.abs(dy)));
            } else {
                double dx = s.getX() - this.getX();
                double dy = s.getY() - this.getY();
                double ratioYtoX = Math.abs(dy) / Math.abs(dx);
                double unitsX = Math.sqrt(SPEED*SPEED /(ratioYtoX*ratioYtoX + 1));
                move(unitsX * (dx / Math.abs(dx)), (unitsX * ratioYtoX) * (dy / Math.abs(dy)));
            }
        }
    }
}
