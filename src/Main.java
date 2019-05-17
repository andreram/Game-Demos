import javax.swing.*;

public class Main {
    private PPanel p;
    public static final int REFRESH_RATE = 10;

    public Main() {
        JFrame frame = new JFrame("Test");
        p = new PPanel();
        frame.add(p);
        //frame.addKeyListener(new KeyHandler());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        Timer t = new Timer(REFRESH_RATE, e -> p.update());

//        Audio a = new Audio();
//        a.play();
        t.start();
    }

    public static void main(String[] args) {
        new Main();
    }

//    private class KeyHandler extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            p.keyPressed(e);
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            p.keyReleased(e);
//        }
//    }
}
