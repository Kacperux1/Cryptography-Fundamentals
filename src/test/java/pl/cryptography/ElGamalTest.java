package pl.cryptography;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.cryptography.DigitalSignature.ElGamal;
import pl.cryptography.DigitalSignature.Signature;

public class ElGamalTest {

    private ElGamal elGamal;
    private byte[] data;

    @BeforeEach
    public void setUp() {


        data = "This is a test message!".getBytes();
    }

    @Test
    public void testSignAndVerify() {
        elGamal = new ElGamal();
        Signature signature = elGamal.encipher(data);
        BigInteger r = signature.getR();
        BigInteger s = signature.getS();

        boolean isVerified = elGamal.verify(data, r, s);


        assertTrue(isVerified);
    }

    @Test
    public void testVerifyModifiedMessage() {
        elGamal = new ElGamal();
        Signature signature = elGamal.encipher(data);
        BigInteger r = signature.getR();  // r część podpisu
        BigInteger s = signature.getS();  // s część podpisu

        byte[] alteredData = "This is a modified message!".getBytes();
        boolean isAlteredVerified = elGamal.verify(alteredData, r, s);

        assertFalse(isAlteredVerified);
    }
}
