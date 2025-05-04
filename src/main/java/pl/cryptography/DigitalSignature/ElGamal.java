package pl.cryptography.DigitalSignature;

import java.math.BigInteger;
import java.security.SecureRandom;


//lepiej chyba zeby juz w metodzie podawac a nie jako pole
public class ElGamal {

    private BigInteger p;   // Liczba pierwsza p
    private BigInteger g;   // Generator g
    private BigInteger a;   // Klucz prywatny
    private BigInteger h;   // Klucz publiczny (h = g^a mod p)

    private final int bitLength = 512; // Długość bitów dla liczby p

    public ElGamal() {

        this.g = new BigInteger("2");  // Generator g,
        this.p = generatePrime(bitLength);  // Liczba pierwsza p
        this.a = generatePrivateKey();  // Klucz prywatny a
        this.h = generatePublicKey();  // Klucz publiczny h = g^a mod p
    }

    public Signature encipher(byte[] data) {
        MD5 md5 = new MD5();
        BigInteger hash = md5.hashFunction(data);
        SecureRandom rand = new SecureRandom();

        BigInteger k;
        BigInteger r;
        BigInteger s;

        do {
            k = new BigInteger(p.bitLength() - 1, rand);
        } while (k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) != 0); // Zapewnienie, że k jest względnie pierwsze z p-1

        do {
            r = g.modPow(k, p).mod(p.subtract(BigInteger.ONE));
        } while (r.equals(BigInteger.ZERO));  // Zapewnienie, że r != 0

        BigInteger kInverse = k.modInverse(p.subtract(BigInteger.ONE));  // Odwrotność k mod (p-1)
        s = kInverse.multiply(hash.subtract(a.multiply(r))).mod(p.subtract(BigInteger.ONE));  // s = k^(-1) * (H(m) - a * r)

        return new Signature(r, s);
    }


    public boolean verify(byte[] data, BigInteger r, BigInteger s) {
        MD5 md5 = new MD5();
        BigInteger hash = md5.hashFunction(data);

        BigInteger left = g.modPow(hash, p);
        BigInteger right = h.modPow(r, p).multiply(r.modPow(s, p)).mod(p);

        return left.equals(right);
    }


    private BigInteger generatePrivateKey() {
        SecureRandom rand = new SecureRandom();
        // Losowanie klucza prywatnego a z zakresu [1, p-2]
        BigInteger pMinusTwo = p.subtract(BigInteger.TWO);
        BigInteger a;
        do {
            a = new BigInteger(p.bitLength() - 1, rand);  // Generowanie liczby z odpowiedniej długości bitów
        } while (a.compareTo(BigInteger.ONE) < 0 || a.compareTo(pMinusTwo) > 0);
        return a;
    }

    // Generowanie klucza publicznego h = g^a mod p
    private BigInteger generatePublicKey() {
        return g.modPow(a, p);  // g^a mod p
    }

    // Funkcja do generowania liczby pierwszej p o zadanej długości bitów
    private BigInteger generatePrime(int bitLength) {
        SecureRandom rand = new SecureRandom();
        // Generowanie prawdopodobnej liczby pierwszej
        return new BigInteger(bitLength, 100, rand);
    }

    public BigInteger getPublicKey() {
        return h;
    }

}

