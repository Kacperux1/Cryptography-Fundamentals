package pl.cryptography.dataAccess;

import java.util.Random;

public class KeyGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static byte[] generateKey(int size) {
        StringBuilder key = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < size; i++){
            key.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }
        return key.toString().getBytes();
    }




}
