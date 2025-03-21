package pl.cryptography.dataAccess;

import java.util.Random;

public class KeyGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static byte[] generateKey16 () {
        String key = "";
        Random rand = new Random();
        for(int i = 0; i < 16; i++){
            key += CHARACTERS.charAt(rand.nextInt(CHARACTERS.length()));
        }
        return key.getBytes();
    }
}
