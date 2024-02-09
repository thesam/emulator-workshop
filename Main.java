import javax.swing.*;

public class Main {

    public static void main(String[] args) {
            Gui gui = new Gui();
            Emulator emulator = new Emulator(gui);
            gui.show();
            emulator.run();
            System.exit(0);
    }

}
