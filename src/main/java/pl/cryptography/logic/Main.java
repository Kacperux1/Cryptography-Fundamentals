package pl.cryptography.logic;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        AES aes = new AES();

        byte[] message1 = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a
        };

        byte[] key1 = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c
        };

        byte[] encoded = aes.encode(message1, key1);
        byte[] decoded = aes.decode(encoded, key1);


        System.out.println(toHexString(message1));
        System.out.println();
        System.out.println(toHexString(encoded));
        System.out.println();
        System.out.println(toHexString(decoded));

        System.out.println("/////////////////////////////////////////////////////////////");

        byte[] message2 = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37,
                (byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07,
                (byte) 0xa8, (byte) 0x8d, (byte) 0x5a, (byte) 0x55
        };

        byte[] key2 = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c
        };

        encoded = aes.encode(message2, key2);
        decoded = aes.decode(encoded, key2);


        System.out.println(toHexString(message2));
        System.out.println();
        System.out.println(toHexString(encoded));
        System.out.println();
        System.out.println(toHexString(decoded));
    }


    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X ", b)); // Formatowanie bajtu do 2-cyfrowego HEX
        }
        return sb.toString().trim();
    }
}