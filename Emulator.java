import java.awt.event.KeyEvent;
import java.util.Random;

public class Emulator {
    private final Gui gui;

    private byte[] memory = new byte[4096];
    private int programCounter = 0x200;
    private int indexRegister = 0;
    private int[] registers = new int[255];

    public Emulator(Gui gui) {
        this.gui = gui;
    }

    public void run(byte[] input) {
        System.arraycopy(input, 0, memory, 0x200, input.length);
        while (true) {
            byte first = memory[programCounter++];
            byte second = memory[programCounter++];
            int instruction = (first & 0xf0) >> 4;
            int x = first & 0x0f;
            int y = (second & 0xf0) >> 4;
            int n = second & 0x0f;
            int nn = second;
            int nnn = (x << 8) | second;

            switch (instruction) {
                case 0:
                    clearScreen();
                    break;
                case 1:
                    jump(nnn);
                    break;
                case 6:
                    setRegister(x, nn);
                    break;
                case 7:
                    addValueToRegister(x, nn);
                    break;
                case 0xA:
                    setIndexRegister(nnn);
                    break;
                case 0xD:
                    draw(x, y, n);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("%x%x", first, second));
            }
        }
    }

    private void draw(int x, int y, int n) {
        debug(String.format("draw %x : %x : %x", x, y, n));
        int xValue = registers[x];
        int yValue = registers[y];
        for (int row = 0; row < n; row++) {
            int spriteByte = memory[indexRegister+row];
            debug(String.format("%x : " , indexRegister+n) + Integer.toBinaryString(spriteByte));
            for (int col = 0; col < 8; col++) {
                if ((spriteByte & (1 << 7-col)) > 0) {
                    gui.setPixel(xValue + col, yValue + row);
                }
            }
        }
    }

    private void setIndexRegister(int address) {
        debug(String.format("set index %x", address));
        this.indexRegister = address;
    }

    private void addValueToRegister(int x, int nn) {
        debug(String.format("add to register %x : %x", x, nn));
        registers[x] += nn;
    }

    private void setRegister(int x, int nn) {
        debug(String.format("set register %x : %x", x, nn));
        registers[x] = nn;
    }

    private void jump(int address) {
        debug(String.format("jump %x", address));
        programCounter = address;
    }

    private void clearScreen() {
        debug("clearScreen");
        gui.clear();
    }

    private void debug(String msg) {
        System.err.println(String.format("%x : ",programCounter) + msg);
    }
}
