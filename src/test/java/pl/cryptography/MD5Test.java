package pl.cryptography;

import org.junit.jupiter.api.Test;
import pl.cryptography.DigitalSignature.MD5;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MD5Test {

    private String toHex(BigInteger bigInteger) {
        String hex = bigInteger.toString(16);
        while (hex.length() < 32) {
            hex = "0" + hex;
        }
        return hex;
    }

    @Test
    void testEmptyString() {
        MD5 md5 = new MD5();
        byte[] input = "".getBytes(StandardCharsets.UTF_8);
        BigInteger result = md5.hashFunction(input);
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", toHex(result));
    }

    @Test
    void testSingleLetter() {
        MD5 md5 = new MD5();
        byte[] input = "a".getBytes(StandardCharsets.UTF_8);
        BigInteger result = md5.hashFunction(input);
        assertEquals("0cc175b9c0f1b6a831c399e269772661", toHex(result));
    }

    @Test
    void testSimpleWord() {
        MD5 md5 = new MD5();
        byte[] input = "abc".getBytes(StandardCharsets.UTF_8);
        BigInteger result = md5.hashFunction(input);
        assertEquals("900150983cd24fb0d6963f7d28e17f72", toHex(result));
    }

    @Test
    void testPhrase() {
        MD5 md5 = new MD5();
        byte[] input = "message digest".getBytes(StandardCharsets.UTF_8);
        BigInteger result = md5.hashFunction(input);
        assertEquals("f96b697d7cb7938d525a2f31aaf161d0", toHex(result));
    }

    @Test
    void testLongerMessage() {
        MD5 md5 = new MD5();
        byte[] input = "abcdefghijklmnopqrstuvwxyz".getBytes(StandardCharsets.UTF_8);
        BigInteger result = md5.hashFunction(input);
        assertEquals("c3fcd3d76192e4007dfb496cca67e13b", toHex(result));
    }
}
