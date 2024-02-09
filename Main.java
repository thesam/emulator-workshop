import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        byte[] input = Files.readAllBytes(Path.of("test_opcode.ch8"));
        Gui gui = new Gui();
        Emulator emulator = new Emulator(gui);
        gui.show();
        emulator.run(input);
        System.exit(0);
    }

}
