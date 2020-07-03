package correcter;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Decoder extends Correcter {
    private final StringBuilder out = new StringBuilder();

    void correct() {

        for (final byte b : bytes) {
            final String in = Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);

            final int b1 = in.charAt(0) - '0';
            final int b2 = in.charAt(1) - '0';
            final int b3 = in.charAt(2) - '0';
            final int b4 = in.charAt(3) - '0';
            final int b5 = in.charAt(4) - '0';
            final int b6 = in.charAt(5) - '0';
            final int b7 = in.charAt(6) - '0';
            final int err = b1 != b2 ? 0 : b3 != b4 ? 1 : b5 != b6 ? 2 : 3;

            if (err < 3) {
                if (err == 0) {
                    final int cor = b3 ^ b5 ^ b7;
                    out.append(cor);
                    out.append(b3);
                    out.append(b5);
                } else if (err == 1) {
                    final int cor = b1 ^ b5 ^ b7;
                    out.append(b1);
                    out.append(cor);
                    out.append(b5);
                } else if (err == 2) {
                    final int cor = b1 ^ b3 ^ b7;
                    out.append(b1);
                    out.append(b3);
                    out.append(cor);
                }
            } else {
                out.append(b1);
                out.append(b3);
                out.append(b5);
            }
        }
    }


    void decodeHamming() {
        for (byte b : bytes) {
            String bits = Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
            int p1 = bits.charAt(0) == '0' ? 0 : 1;
            int p2 = bits.charAt(1) == '0' ? 0 : 1;
            int d1 = bits.charAt(2) == '0' ? 0 : 1;
            int p3 = bits.charAt(3) == '0' ? 0 : 1;
            int d2 = bits.charAt(4) == '0' ? 0 : 1;
            int d3 = bits.charAt(5) == '0' ? 0 : 1;
            int d4 = bits.charAt(6) == '0' ? 0 : 1;

            int pc1 = (d1 + d2 + d4) % 2 == 0 ? 0 : 1;
            int pc2 = (d1 + d3 + d4) % 2 == 0 ? 0 : 1;
            int pc3 = (d2 + d3 + d4) % 2 == 0 ? 0 : 1;

            boolean p1Error = p1 != pc1;
            boolean p2Error = p2 != pc2;
            boolean p3Error = p3 != pc3;

            if (p1Error && p2Error && p3Error) {
                d4 = ~d4 & 1;
            } else if (p2Error && p3Error) {
                d3 = ~d3 & 1;
            } else if (p1Error && p2Error) {
                d1 = ~d1 & 1;
            } else if (p1Error && p3Error) {
                d2 = ~d2 & 1;
            }
            bits = String.format("%d%d%d%d", d1, d2, d3, d4);
            out.append(bits);

        }
    }

    void writeChars(String fileName) {
        try (PrintStream pw = new PrintStream(fileName)) {
            for (int i = 0; i < out.length() - out.length() % 8; i += 8) {
                pw.print((char) Integer.parseInt(out.substring(i, i + 8), 2));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
