import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gui {

    private final JFrame frame;
    private final BufferedImage screen;

    public Gui() {
        screen = new BufferedImage(64,32,BufferedImage.TYPE_INT_RGB);
        frame = new JFrame("Emulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainPanel panel = new MainPanel();
        panel.setPreferredSize(new Dimension(640,320));
        frame.add(panel);
    }

    public void start() {
        frame.setVisible(true);
        frame.pack();
    }

    class MainPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(screen,0,0,640,320,null);
        }
    }
}
