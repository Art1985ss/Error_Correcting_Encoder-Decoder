package correcter;

import java.util.Random;

public class Sender extends Correcter {
    void corrupt() {
        final Random generate = new Random();

        for (final byte b : bytes) {
            encBytes.add((byte) (b ^ 1 << generate.nextInt(8)));
        }
    }
}
