package pl.cryptography.DigitalSignature;

import java.math.BigInteger;

public class MD5 {

    private int A;
    private int B;
    private int C;
    private int D;

    private static final int[] K = {
            0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
            0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
            0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
            0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
            0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
            0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
            0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
            0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
            0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
            0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
            0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
            0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
            0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
            0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
            0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
            0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391
    };

    private static final int[] S = {
            7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
            5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
            4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
            6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    public BigInteger hashFunction(byte[] data) {
        // Resetowanie stanu!
        A = 0x67452301;
        B = 0xefcdab89;
        C = 0x98badcfe;
        D = 0x10325476;

        byte[] paddedData = appendData(data);

        for (int i = 0; i < paddedData.length / 64; i++) {
            byte[] block = new byte[64];
            System.arraycopy(paddedData, i * 64, block, 0, 64);
            processBlock(block);
        }

        byte[] digest = new byte[16];
        encodeIntToBytes(A, digest, 0);
        encodeIntToBytes(B, digest, 4);
        encodeIntToBytes(C, digest, 8);
        encodeIntToBytes(D, digest, 12);

        return new BigInteger(1, digest);
    }

    private byte[] appendData(byte[] data) {
        int baseDatalength = data.length;
        long messageLengthBits = (long) baseDatalength * 8; // WAŻNE: long!

        int paddingLength = (56 - (baseDatalength + 1) % 64 + 64) % 64;
        byte[] appendedMessage = new byte[baseDatalength + 1 + paddingLength + 8];

        System.arraycopy(data, 0, appendedMessage, 0, baseDatalength);
        appendedMessage[baseDatalength] = (byte) 0x80;

        int len = baseDatalength + 1 + paddingLength;
        for (int i = 0; i < 8; i++) {
            appendedMessage[len + i] = (byte) ((messageLengthBits >>> (8 * i)) & 0xFF);
        }

        return appendedMessage;
    }

    private void processBlock(byte[] block) {
        if (block.length != 64) {
            throw new IllegalArgumentException("Invalid block length");
        }

        int[] M = new int[16];
        for (int i = 0; i < 16; i++) {
            M[i] = (block[i * 4] & 0xFF)
                    | ((block[i * 4 + 1] & 0xFF) << 8)
                    | ((block[i * 4 + 2] & 0xFF) << 16)
                    | ((block[i * 4 + 3] & 0xFF) << 24);
        }

        int a = A;
        int b = B;
        int c = C;
        int d = D;

        for (int i = 0; i < 64; i++) {
            int F, g;

            if (i < 16) {
                F = (b & c) | (~b & d);
                g = i;
            } else if (i < 32) {
                F = (d & b) | (~d & c);
                g = (5 * i + 1) % 16;
            } else if (i < 48) {
                F = b ^ c ^ d;
                g = (3 * i + 5) % 16;
            } else {
                F = c ^ (b | ~d);
                g = (7 * i) % 16;
            }

            int temp = d;
            d = c;
            c = b;
            b = b + Integer.rotateLeft(a + F + K[i] + M[g], S[i]);
            a = temp;
        }

        A += a;
        B += b;
        C += c;
        D += d;
    }

    private void encodeIntToBytes(int value, byte[] output, int offset) {
        output[offset] = (byte) (value & 0xFF);
        output[offset + 1] = (byte) ((value >>> 8) & 0xFF);
        output[offset + 2] = (byte) ((value >>> 16) & 0xFF);
        output[offset + 3] = (byte) ((value >>> 24) & 0xFF);
    }
}
