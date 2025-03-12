package pl.cryptography.logic;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {
    public static void main(String[] args) throws Exception {
        AES aes = new AES();

        byte[] message = {
                (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
                (byte) 0x9A, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0,
                (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44,
                (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88
        };

        byte[] key = {
                (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD,
                (byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22,
                (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66,
                (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00
        };

        // Tworzymy klucz AES 128-bitowy
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        // Inicjalizujemy szyfr AES w trybie ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Szyfrujemy wiadomość
        byte[] encryptedMessage = cipher.doFinal(message);
        byte[] myencryptedMessage = aes.encode(message, key);

        // Wypisujemy wynik jako ciąg szesnastkowy
        StringBuilder hexString = new StringBuilder();
        for (byte b : encryptedMessage) {
            hexString.append(String.format("%02X", b)); // %02X zapewnia dwucyfrowy format szesnastkowy
        }

        StringBuilder hexString2 = new StringBuilder();
        for (byte b : myencryptedMessage) {
            hexString2.append(String.format("%02X", b)); // %02X zapewnia dwucyfrowy format szesnastkowy
        }

        // Wyświetlamy wynik
        System.out.println("Encrypted message: " + hexString.toString());
        System.out.println("Encrypted message: " + hexString2.toString());
    }
}