package correcter;

public class Encoder extends Correcter {
    void encode() {
        StringBuilder encByte = new StringBuilder();
        int lastbit = 0;
        for (final char b : getBitString().toCharArray()) {
            encByte.append(b);
            encByte.append(b);
            lastbit ^= b == '1' ? 1 : 0;

            if (encByte.length() == 6) {
                encByte.append(lastbit);
                encByte.append(lastbit);
                encBytes.add((byte) Integer.parseInt(encByte.toString(), 2));
                lastbit = 0;
                encByte = new StringBuilder();
            }
        }

        if (encByte.length() != 0) {
            while (encByte.length() != 6) {
                encByte.append(0);
            }
            encByte.append(lastbit);
            encByte.append(lastbit);
            encBytes.add((byte) Integer.parseInt(encByte.toString(), 2));
        }
    }

    void encodeHumming() {
        StringBuilder temp = new StringBuilder();
        for (char ch : getBitString().toCharArray()) {
            temp.append(ch);
            if (temp.length() == 4) {
                temp = new StringBuilder(enc(temp.toString()));
                encBytes.add((byte) Integer.parseInt(temp.toString(), 2));
                temp = new StringBuilder();
            }
        }

        if (temp.length() != 0) {
            while (temp.length() != 4) {
                temp.append('0');
            }
            temp = new StringBuilder(enc(temp.toString()));
            encBytes.add((byte) Integer.parseInt(temp.toString(), 2));
        }
    }

    String enc(String str) {
        System.out.println("str = " + str);
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int d1 = str.charAt(0) == '0' ? 0 : 1;
        int d2 = str.charAt(1) == '0' ? 0 : 1;
        int d3 = str.charAt(2) == '0' ? 0 : 1;
        int d4 = str.charAt(3) == '0' ? 0 : 1;
        p1 = (p1 + d1 + d2 + d4) % 2 == 0 ? 0 : 1;
        p2 = (p2 + d1 + d3 + d4) % 2 == 0 ? 0 : 1;
        p3 = (p3 + d2 + d3 + d4) % 2 == 0 ? 0 : 1;
        return String.format("%d%d%d%d%d%d%d%d", p1, p2, d1, p3, d2, d3, d4, 0);
    }
}
