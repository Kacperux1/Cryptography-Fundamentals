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

    }

    public Signature encipher(byte[] data) {
        MD5 md5 = new MD5();
        BigInteger hash = md5.hashFunction(data);
        SecureRandom rand = new SecureRandom();

        BigInteger k;
        BigInteger r;
        BigInteger s;


        do {
            // losuj k takie, że gcd(k, p-1) == 1
            do {
                k = new BigInteger(p.bitLength() - 1, rand);
            } while (!k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)); //zapewnienie , ze k jest wzglednie pierwsze

            r = g.modPow(k, p).mod(p.subtract(BigInteger.ONE)); //oblicz r

        } while (r.equals(BigInteger.ZERO)); // jeśli r = 0, losuj nowe k i próbuj od nowa


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


    public BigInteger generatePrivateKey() {
        SecureRandom rand = new SecureRandom();
        // Losowanie klucza prywatnego a z zakresu [1, p-2]
        BigInteger pMinusTwo = p.subtract(BigInteger.TWO);
        BigInteger a;
        do {
            a = new BigInteger(p.bitLength() - 2, rand);  // Generowanie liczby z odpowiedniej długości bitów
        } while (a.compareTo(BigInteger.ONE) < 0 || a.compareTo(pMinusTwo) > 0);
        return a;
    }

    // Generowanie klucza publicznego h = g^a mod p
    public BigInteger generatePublicKey() {
        return g.modPow(a, p);  // g^a mod p
    }

    // Funkcja do generowania liczby pierwszej p o zadanej długości bitów
    public BigInteger generatePrime() {
        SecureRandom rand = new SecureRandom();
        // Generowanie prawdopodobnej liczby pierwszej
        return new BigInteger(bitLength, 100, rand);
    }

    public BigInteger generateG() {
        SecureRandom rand = new SecureRandom();
        return new BigInteger(bitLength-2,  rand);
    }

    public BigInteger getPublicKey() {
        return h;
    }


    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public void setH(BigInteger h) {
        this.h = h;
    }
}

