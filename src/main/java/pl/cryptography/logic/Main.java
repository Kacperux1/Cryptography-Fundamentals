package pl.cryptography.logic;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        AES aes = new AES();

        byte[] message = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37,
                (byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07,
                (byte) 0xa8, (byte) 0x8d, (byte) 0xa2, (byte) 0x34
        };

        byte[] key = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c
        };

        // Tworzymy klucz AES 128-bitowy
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        System.out.println(bytesToHex(secretKey.getEncoded()));
        System.out.println(bytesToHex(key));

        // Inicjalizujemy szyfr AES w trybie ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Szyfrujemy wiadomość
        byte[] encryptedMessage = cipher.doFinal(message);
        byte[] myEncryptedMessage = aes.encode(message, key);

        // Porównanie wyników
        boolean areEqual = Arrays.equals(encryptedMessage, myEncryptedMessage);

        // Wypisujemy wynik jako ciąg szesnastkowy
        System.out.println("Encrypted message: " + bytesToHex(encryptedMessage));
        System.out.println("My encrypted message: " + bytesToHex(myEncryptedMessage));
        System.out.println("Czy wyniki są takie same? " + areEqual);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X ", b));
        }
        return hexString.toString().trim();
    }
}