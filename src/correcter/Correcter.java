package correcter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Correcter {
    protected byte[] bytes;
    protected List<Byte> encBytes = new ArrayList<>();
    private final StringBuilder bits = new StringBuilder();

    public Correcter() {
    }

    public void readFromFile(String fileName) {
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (final byte b : bytes) {
            bits.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        }
    }

    public void saveToFile(String fileName) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            for (byte b : encBytes) {
                outputStream.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBitString() {
        return bits.toString();
    }
}
