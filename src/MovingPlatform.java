import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingPlatform extends Platform {

    private int dx_dt;
    private int dy_dt;
    private int time;
    private Direction dir;

    public MovingPlatform(int x, int y, int lengthx, int lengthy, int p, int dx, int dy) {
        super(x, y, lengthx, lengthy);

        dx_dt = dx;
        dy_dt = dy;
        time = 0;
        setDir();

        Timer t = new Timer(Main.REFRESH_RATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += Main.REFRESH_RATE;
                if (time / p != 0) {
                    time = 0;
                    dx_dt = -dx_dt;
                    dy_dt = -dy_dt;
                    setDir();
                }
                move(dx_dt, dy_dt);
            }
        });

        t.start();
    }

    public void setDir() {
        if (dx_dt > 0)
            dir = Direction.RIGHT;
        else if (dx_dt < 0)
            dir = Direction.LEFT;
        else if (dy_dt > 0)
            dir = Direction.DOWN;
        else if (dy_dt < 0)
            dir = Direction.UP;
        else
            dir = Direction.NEUTRAL;
    }

    public void move(int dx, int dy) {
        changeX(dx);
        changeY(dy);
    }

    public int getDx() {
        return dx_dt;
    }

    public int getDy() {
        return dy_dt;
    }
}
