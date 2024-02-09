import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;

public class Emulator {
    private final Gui gui;

    private byte[] memory = new byte[4096];
    private int programCounter = 0x200;
    private int indexRegister = 0;
    private byte[] registers = new byte[16];
    private Stack<Integer> stack = new Stack<>();

    public Emulator(Gui gui) {
        this.gui = gui;
    }

    public void run(byte[] input) {
        System.arraycopy(input, 0, memory, 0x200, input.length);
        while (true) {
            int first = memory[programCounter++] & 0xff;
            int second = memory[programCounter++] & 0xff;
            int instruction = (first & 0xf0) >> 4;
            int x = first & 0x0f;
            int y = (second & 0xf0) >> 4;
            int n = second & 0x0f;
            int nn = second;
            int nnn = (x << 8) | second;

            switch (instruction) {
                case 0 -> {
                    if (second == 0xe0) {
                        clearScreen();
                    } else if (second == 0xee) {
                        returnFromSub();
                    }
                }
                case 1 -> jump(nnn);
                case 2 -> call(nnn);
                case 3 -> skipIfEqual(x, nn);
                case 4 -> skipIfNotEqual(x, nn);
                case 5 -> skipIfEqual2(x, y);
                case 6 -> setRegister(x, nn);
                case 7 -> addValueToRegister(x, nn);
                case 8 -> {
                    switch (n) {
                        case 0 -> setXtoY(x, y);
                        case 1 -> binaryOr(x, y);
                        case 2 -> binaryAnd(x, y);
                        case 3 -> binaryXor(x, y);
                        case 4 -> binaryAdd(x, y);
                        case 5 -> binarySub(x, y, x);
                        case 7 -> binarySub(y, x, x);
                        default -> throw new UnsupportedOperationException("" + n);
                    }
                }
                case 9 -> skipIfNotEqual2(x, y);
                case 0xA -> setIndexRegister(nnn);
                case 0xD -> draw(x, y, n);
                case 0xF -> storeToMemory(x);
                default -> throw new UnsupportedOperationException(String.format("%x%x", first, second));
            }
        }
    }

    private void binarySub(int first, int second, int x) {
        registers[x] = (byte)(registers[first] - registers[second]);
    }

    private void binaryAdd(int x, int y) {
        int a = registers[x];
        int b = registers[y];
        int sum = a + b;
        if (sum > 255) {
            registers[0xf] = 1;
        } else {
            registers[0xf] = 0;
        }
        registers[x] = (byte) (sum & 0xff);
    }

    private void binaryXor(int x, int y) {
        registers[x] = (byte) ((registers[x] ^ registers[y]) & 0xff);
    }

    private void binaryAnd(int x, int y) {
        registers[x] = (byte) ((registers[x] & registers[y]) & 0xff);
    }

    private void binaryOr(int x, int y) {
        registers[x] = (byte) ((registers[x] | registers[y]) & 0xff);
    }

    private void storeToMemory(int x) {
        for (int i = 0; i <= x; i++) {
            memory[indexRegister + i] = registers[i];
        }
    }

    private void setXtoY(int x, int y) {
        registers[x] = registers[y];
    }

    private void returnFromSub() {
        Integer pc = this.stack.pop();
        jump(pc);
    }

    private void call(int nnn) {
        this.stack.push(this.programCounter);
        jump(nnn);
    }

    private void skipIfNotEqual2(int x, int y) {
        if (registers[x] != registers[y]) {
            programCounter += 2;
        }
    }

    private void skipIfEqual2(int x, int y) {
        if (registers[x] == registers[y]) {
            programCounter += 2;
        }
    }

    private void skipIfNotEqual(int x, int nn) {
        if (registers[x] != nn) {
            programCounter += 2;
        }
    }

    private void skipIfEqual(int x, int nn) {
        if (registers[x] == nn) {
            programCounter += 2;
        }
    }

    private void draw(int x, int y, int n) {
        debug(String.format("draw %x : %x : %x", x, y, n));
        int xValue = registers[x];
        int yValue = registers[y];
        for (int row = 0; row < n; row++) {
            int spriteByte = memory[indexRegister + row];
            debug(String.format("%x : ", indexRegister + n) + Integer.toBinaryString(spriteByte));
            for (int col = 0; col < 8; col++) {
                if ((spriteByte & (1 << 7 - col)) > 0) {
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
        registers[x] += nn & 0xff;
    }

    private void setRegister(int x, int nn) {
        debug(String.format("set register %x : %x", x, nn));
        registers[x] = (byte) (nn & 0xff);
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
        //System.err.println(String.format("%x : ",programCounter) + msg);
    }
}
