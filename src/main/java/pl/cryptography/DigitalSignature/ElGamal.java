package pl.cryptography.DigitalSignature;

import java.math.BigInteger;
import java.security.SecureRandom;




public class ElGamal {

    private BigInteger p;
    private BigInteger g;
    private BigInteger a;
    private BigInteger h;

    private final int bitLength = 512;

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

            do {
                k = new BigInteger(p.bitLength() - 1, rand);
            } while (!k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

            r = g.modPow(k, p).mod(p.subtract(BigInteger.ONE));

        } while (r.equals(BigInteger.ZERO));


        BigInteger kInverse = k.modInverse(p.subtract(BigInteger.ONE));
        s = kInverse.multiply(hash.subtract(a.multiply(r))).mod(p.subtract(BigInteger.ONE));

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

        BigInteger pMinusTwo = p.subtract(BigInteger.TWO);
        BigInteger a;
        do {
            a = new BigInteger(p.bitLength() - 2, rand);
        } while (a.compareTo(BigInteger.ONE) < 0 || a.compareTo(pMinusTwo) > 0);
        return a;
    }


    public BigInteger generatePublicKey() {
        return g.modPow(a, p);
    }


    public BigInteger generatePrime() {
        SecureRandom rand = new SecureRandom();

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

