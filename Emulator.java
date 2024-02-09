import java.awt.event.KeyEvent;
import java.util.Random;

public class Emulator {
    private final Gui gui;

    public Emulator(Gui gui) {
        this.gui = gui;
    }

    public void run() {
        // TODO: Replace with fetch-decode-execute-loop
        Random random = new Random();
        while(true) {
            if (gui.isKeyPressed(KeyEvent.VK_Q)) {
                break;
            }
            if (gui.isKeyPressed(KeyEvent.VK_S)) {
                gui.setPixel(random.nextInt(64),random.nextInt(32));
            }
        }
    }
}
