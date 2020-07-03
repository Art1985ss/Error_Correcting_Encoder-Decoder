package correcter;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final String mode = sc.next();
        sc.close();

        switch (mode) {
            case "encode":
                final Encoder enc = new Encoder();
                enc.readFromFile("send.txt");
                enc.encodeHumming();
                enc.saveToFile("encoded.txt");
                break;

            case "send":
                final Sender send = new Sender();
                send.readFromFile("encoded.txt");
                send.corrupt();
                send.saveToFile("received.txt");
                break;

            case "decode":
                final Decoder dec = new Decoder();
                dec.readFromFile("received.txt");
                dec.decodeHamming();
                dec.writeChars("decoded.txt");
                break;
        }
    }
}
