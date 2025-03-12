package pl.cryptography.view;
import Logic.AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        AES aes = new AES();

        // Przykładowa wiadomość i klucz (upewnij się, że masz taki sam klucz w Twojej funkcji)
        byte[] message = "Hello, world!".getBytes();
        byte[] key = new byte[16]; // Przykładowy 128-bitowy klucz AES, pamiętaj aby użyć taki sam w swojej funkcji

        // Wypełnienie klucza przykładowymi danymi (możesz zmienić to na swój klucz)
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) (i + 1);
        }

        // Szyfrowanie przy użyciu standardowej funkcji AES w Java
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedMessage = cipher.doFinal(message);

        // Wydrukuj zaszyfrowaną wiadomość w postaci Base64
        String encryptedMessageBase64 = Base64.getEncoder().encodeToString(encryptedMessage);
        System.out.println("Encrypted using Java Cipher: " + encryptedMessageBase64);

        // Teraz użyj swojej własnej funkcji szyfrującej i porównaj wyniki
        byte[] myEncryptedMessage = aes.encode(message, key); // Zamień na swoją funkcję szyfrującą

        String myEncryptedMessageBase64 = Base64.getEncoder().encodeToString(myEncryptedMessage);
        System.out.println("Encrypted using my function: " + myEncryptedMessageBase64);

        // Porównanie wyników
        if (encryptedMessageBase64.equals(myEncryptedMessageBase64)) {
            System.out.println("The results are the same!");
        } else {
            System.out.println("The results are different!");
        }
    }
}