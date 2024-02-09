import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        byte[] input = Files.readAllBytes(Path.of("INPUT.ROM"));
        Gui gui = new Gui();
        Emulator emulator = new Emulator(gui);
        gui.show();
        emulator.run(input);
        System.exit(0);
    }

}
