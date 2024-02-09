import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

public class Gui {

    private BufferedImage screen;
    private Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();

    private int WHITE = Color.white.getRGB();
    private int BLACK = Color.black.getRGB();
    private MainPanel panel;

    public void show() {
        SwingUtilities.invokeLater(() -> {
            screen = new BufferedImage(64, 32, BufferedImage.TYPE_INT_RGB);
            JFrame frame = new JFrame("Emulator");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            panel = new MainPanel();
            panel.setPreferredSize(new Dimension(640, 320));
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public void setPixel(int x, int y) {
        this.screen.setRGB(x, y, WHITE);
        panel.repaint();
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    class MainPanel extends JPanel implements KeyListener {

        public MainPanel() {
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(this);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(screen, 0, 0, 640, 320, null);
        }

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            pressedKeys.add(keyEvent.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            pressedKeys.remove(keyEvent.getKeyCode());
        }
    }
}
