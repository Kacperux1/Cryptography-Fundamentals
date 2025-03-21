package pl.cryptography;

import org.junit.jupiter.api.Test;
import pl.cryptography.logic.AES;

import static org.junit.jupiter.api.Assertions.*;

public class AESTest {
    private AES aes = new AES();


    @Test
    public void addingBytesTest1() {
        byte[] message = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37,
                (byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07,
                (byte) 0xa8, (byte) 0x8d};

        byte[] key = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c};

        byte[] cipher = aes.encode(message, key);
        byte[] plainText = aes.decode(cipher, key);

        for (int j = 0; j < message.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }

        for (int j = 0; j < plainText.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }
    }

    @Test
    public void addingBytesTest2() {
        byte[] message = {(byte) 0x32, (byte) 0x88};

        byte[] key = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c};

        byte[] cipher = aes.encode(message, key);
        byte[] plainText = aes.decode(cipher, key);

        for (int j = 0; j < message.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }

        for (int j = 0; j < plainText.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }
    }

    @Test
    public void decodeTest() {
        byte[] message = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37,
                (byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07,
                (byte) 0xa8, (byte) 0x8d, (byte) 0xa2, (byte) 0x34};

        byte[] key = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c};

        byte[] cipher = aes.encode(message, key);
        byte[] plainText = aes.decode(cipher, key);

        for (int j = 0; j < message.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }

        for (int j = 0; j < plainText.length; j++) {
            assertEquals((message[j] & 0xFF), (plainText[j] & 0xFF));
        }
    }

    @Test
    public void inv_mixColumnsTest() {
        byte[][] blok = {
                {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                {(byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22},
                {(byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66},
                {(byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00}};

        byte[][] half = aes.mixColumns(blok);
        byte[][] result = aes.inv_mixColumns(half);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                assertEquals((blok[i][j] & 0xFF), (result[i][j] &0xFF));
            }
        }

    }

    @Test
    public void inv_shiftRowsTest() {
        byte[][] blok = {
                {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                {(byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22},
                {(byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66},
                {(byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00}};

        byte[][] half = aes.shiftRows(blok);
        byte[][] result = aes.inv_shiftRows(half);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                assertEquals((blok[i][j] & 0xFF), (result[i][j] &0xFF));
            }
        }
    }

    @Test
    public void inv_subBytesTest() {
        byte[][] blok = {{(byte) 0x19, (byte) 0xa0, (byte) 0x9a, (byte) 0xe9},
                {(byte) 0x3d, (byte) 0xf4, (byte) 0xc6, (byte) 0xf8},
                {(byte) 0xe3, (byte) 0xe2, (byte) 0x8d, (byte) 0x48},
                {(byte) 0xbe, (byte) 0x2b, (byte) 0x2a, (byte) 0x08}};

        byte[][] half = aes.subBytes(blok);
        byte[][] effect = aes.inv_subBytes(blok);

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                assertEquals((blok[i][j] & 0xFF), (effect[i][j]) & 0xFF);
            }
        }
    }

    @Test
    public void encodeTest() {
        byte[] message = {
                (byte) 0x32, (byte) 0x88, (byte) 0x31, (byte) 0xe0,
                (byte) 0x43, (byte) 0x5a, (byte) 0x31, (byte) 0x37,
                (byte) 0xf6, (byte) 0x30, (byte) 0x98, (byte) 0x07,
                (byte) 0xa8, (byte) 0x8d, (byte) 0xa2, (byte) 0x34};

        byte[] key = {
                (byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c};

        byte[] expected = {
                (byte) 0x39, (byte) 0x02, (byte) 0xDC, (byte) 0x19,
                (byte) 0x25, (byte) 0xDC, (byte) 0x11, (byte) 0x6A,
                (byte) 0x84, (byte) 0x09, (byte) 0x85, (byte) 0x0B,
                (byte) 0x1D, (byte) 0xFB, (byte) 0x97, (byte) 0x32};

        byte[] result = aes.encode(message, key);

        for (int i = 0; i < result.length; i++) {
                System.out.printf("0x%02X ", result[i] & 0xFF);
        }

        for(int i = 0; i < 4; i++){
            assertEquals((expected[i] & 0xFF), (result[i] & 0xFF));
        }

    }

    @Test
    public void addRoundKeyTest(){
        byte[][] state = {
                {(byte) 0x19, (byte) 0xa0, (byte) 0x9a, (byte) 0xe9},
                {(byte) 0x3d, (byte) 0xf4, (byte) 0xc6, (byte) 0xf8},
                {(byte) 0xe3, (byte) 0xe2, (byte) 0x8d, (byte) 0x48},
                {(byte) 0xbe, (byte) 0x2b, (byte) 0x2a, (byte) 0x08}};

        byte[][] key = {{(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                        {(byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22},
                        {(byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66},
                        {(byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00}};

        byte[][] anticipated = {
                {(byte) 0xB3, (byte) 0x4E, (byte) 0xA9, (byte) 0x9E},
                {(byte) 0x86, (byte) 0x0B, (byte) 0x82, (byte) 0x70},
                {(byte) 0x2F, (byte) 0xF3, (byte) 0xD8, (byte) 0xD1},
                {(byte) 0x63, (byte) 0x09, (byte) 0x4C, (byte) 0x08}};

        byte[][] result = aes.addRoundKey(state, key, 0);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                assertEquals((anticipated[i][j] & 0xFF), (result[i][j] & 0xFF));
            }
        }


    }

    @Test
    public void subBytesTest() {
        byte[][] blok = {{(byte) 0x19, (byte) 0xa0, (byte) 0x9a, (byte) 0xe9},
                         {(byte) 0x3d, (byte) 0xf4, (byte) 0xc6, (byte) 0xf8},
                         {(byte) 0xe3, (byte) 0xe2, (byte) 0x8d, (byte) 0x48},
                         {(byte) 0xbe, (byte) 0x2b, (byte) 0x2a, (byte) 0x08}};


        byte[][] anticipated = {
                {(byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e},
                {(byte) 0x27, (byte) 0xbf, (byte) 0xb4, (byte) 0x41},
                {(byte) 0x11, (byte) 0x98, (byte) 0x5d, (byte) 0x52},
                {(byte) 0xae, (byte) 0xf1, (byte) 0xe5, (byte) 0x30}};

        byte[][] effect = aes.subBytes(blok);
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                assertEquals((anticipated[i][j] & 0xFF), (effect[i][j] & 0xFF));
            }
        }
    }



    @Test
    public void mixColumsTest(){
        byte[][] origin = {
                {(byte) 0x87, (byte) 0xf2, (byte) 0x4d, (byte) 0x97},
                {(byte) 0x6e, (byte) 0x4c, (byte) 0x90, (byte) 0xec},
                {(byte) 0x46, (byte) 0xe7, (byte) 0x4a, (byte) 0xc3},
                {(byte) 0xa6, (byte) 0x8c, (byte) 0xd8, (byte) 0x95}
        };

        byte[][] anticipated = {
                {(byte) 0x47, (byte) 0x40, (byte) 0xa3, (byte) 0x4c},
                {(byte) 0x37, (byte) 0xd4, (byte) 0x70, (byte) 0x9f},
                {(byte) 0x94, (byte) 0xe4, (byte) 0x3a, (byte) 0x42},
                {(byte) 0xed, (byte) 0xa5, (byte) 0xa6, (byte) 0xbc}
        };

        byte[][] after = aes.mixColumns(origin);

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                assertEquals((anticipated[i][j] & 0xFF), (after[i][j]) & 0xFF);
            }
        }

    }

    @Test
    public void fMulTest(){
        byte a = (byte) 0x57;
        byte b = (byte) 0x83;

        byte r = aes.fMul(a, b);
        assertEquals((0xC1 & 0xFF), (r & 0xFF));
    }

    @Test
    public void shiftRowsTest(){
        byte[][] blok = {{(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                         {(byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22},
                         {(byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66},
                         {(byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00}};

        byte[][] blok2 = {{(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                          {(byte) 0xFF, (byte) 0x11, (byte) 0x22, (byte) 0xEE},
                          {(byte) 0x55, (byte) 0x66, (byte) 0x33, (byte) 0x44},
                          {(byte) 0x00, (byte) 0x77, (byte) 0x88, (byte) 0x99}};

        blok = aes.shiftRows(blok);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                assertEquals((blok[i][j] & 0xFF), (blok2[i][j] &0xFF));
            }
        }
    }

    @Test
    public void subRowTest() {
        byte[] row = {(byte) 0x00, (byte) 0x01, (byte) 0xFF, (byte) 0xFE};

        byte[] row2 = aes.subRow(row);
        byte[] row3 = {(byte) 0x63, (byte) 0x7C, (byte) 0x16, (byte) 0xBB};

        assertEquals(row3[0] & 0xFF, row2[0] & 0xFF);
        assertEquals(row3[1] & 0xFF, row2[1] & 0xFF);
        assertEquals(row3[2] & 0xFF, row2[2] & 0xFF);
        assertEquals(row3[3] & 0xFF, row2[3] & 0xFF);

    }

    @Test
    public void gTest() {
        byte[] row = {(byte) 0x00, (byte) 0x01, (byte) 0xFF, (byte) 0xFE};

        byte[] row2 = aes.g(row, 1);
        byte[] row3 = {(byte) 0x7D, (byte) 0x16, (byte) 0xBB, (byte) 0x63};

        assertEquals(row3[0] & 0xFF, row2[0] & 0xFF);
        assertEquals(row3[1] & 0xFF, row2[1] & 0xFF);
        assertEquals(row3[2] & 0xFF, row2[2] & 0xFF);
        assertEquals(row3[3] & 0xFF, row2[3] & 0xFF);
    }

    @Test
    public void expandKeyTest() {
        byte[] key = {(byte) 0x2b, (byte) 0x28, (byte) 0xab, (byte) 0x09,
                (byte) 0x7e, (byte) 0xae, (byte) 0xf7, (byte) 0xcf,
                (byte) 0x15, (byte) 0xd2, (byte) 0x15, (byte) 0x4f,
                (byte) 0x16, (byte) 0xa6, (byte) 0x88, (byte) 0x3c};

        byte[][] table = {
                {(byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16},
                {(byte) 0x28, (byte) 0xAE, (byte) 0xD2, (byte) 0xA6},
                {(byte) 0xAB, (byte) 0xF7, (byte) 0x15, (byte) 0x88},
                {(byte) 0x09, (byte) 0xCF, (byte) 0x4F, (byte) 0x3C},
                {(byte) 0xA0, (byte) 0xFA, (byte) 0xFE, (byte) 0x17},
                {(byte) 0x88, (byte) 0x54, (byte) 0x2C, (byte) 0xB1},
                {(byte) 0x23, (byte) 0xA3, (byte) 0x39, (byte) 0x39},
                {(byte) 0x2A, (byte) 0x6C, (byte) 0x76, (byte) 0x05},
                {(byte) 0xF2, (byte) 0xC2, (byte) 0x95, (byte) 0xF2},
                {(byte) 0x7A, (byte) 0x96, (byte) 0xB9, (byte) 0x43},
                {(byte) 0x59, (byte) 0x35, (byte) 0x80, (byte) 0x7A},
                {(byte) 0x73, (byte) 0x59, (byte) 0xF6, (byte) 0x7F},
                {(byte) 0x3D, (byte) 0x80, (byte) 0x47, (byte) 0x7D},
                {(byte) 0x47, (byte) 0x16, (byte) 0xFE, (byte) 0x3E},
                {(byte) 0x1E, (byte) 0x23, (byte) 0x7E, (byte) 0x44},
                {(byte) 0x6D, (byte) 0x7A, (byte) 0x88, (byte) 0x3B},
                {(byte) 0xEF, (byte) 0x44, (byte) 0xA5, (byte) 0x41},
                {(byte) 0xA8, (byte) 0x52, (byte) 0x5B, (byte) 0x7F},
                {(byte) 0xB6, (byte) 0x71, (byte) 0x25, (byte) 0x3B},
                {(byte) 0xDB, (byte) 0x0B, (byte) 0xAD, (byte) 0x00},
                {(byte) 0xD4, (byte) 0xD1, (byte) 0xC6, (byte) 0xF8},
                {(byte) 0x7C, (byte) 0x83, (byte) 0x9D, (byte) 0x87},
                {(byte) 0xCA, (byte) 0xF2, (byte) 0xB8, (byte) 0xBC},
                {(byte) 0x11, (byte) 0xF9, (byte) 0x15, (byte) 0xBC},
                {(byte) 0x6D, (byte) 0x88, (byte) 0xA3, (byte) 0x7A},
                {(byte) 0x11, (byte) 0x0B, (byte) 0x3E, (byte) 0xFD},
                {(byte) 0xDB, (byte) 0xF9, (byte) 0x86, (byte) 0x41},
                {(byte) 0xCA, (byte) 0x00, (byte) 0x93, (byte) 0xFD},
                {(byte) 0x4E, (byte) 0x54, (byte) 0xF7, (byte) 0x0E},
                {(byte) 0x5F, (byte) 0x5F, (byte) 0xC9, (byte) 0xF3},
                {(byte) 0x84, (byte) 0xA6, (byte) 0x4F, (byte) 0xB2},
                {(byte) 0x4E, (byte) 0xA6, (byte) 0xDC, (byte) 0x4F},
                {(byte) 0xEA, (byte) 0xD2, (byte) 0x73, (byte) 0x21},
                {(byte) 0xB5, (byte) 0x8D, (byte) 0xBA, (byte) 0xD2},
                {(byte) 0x31, (byte) 0x2B, (byte) 0xF5, (byte) 0x60},
                {(byte) 0x7F, (byte) 0x8D, (byte) 0x29, (byte) 0x2F},
                {(byte) 0xAC, (byte) 0x77, (byte) 0x66, (byte) 0xF3},
                {(byte) 0x19, (byte) 0xFA, (byte) 0xDC, (byte) 0x21},
                {(byte) 0x28, (byte) 0xD1, (byte) 0x29, (byte) 0x41},
                {(byte) 0x57, (byte) 0x5C, (byte) 0x00, (byte) 0x6E},
                {(byte) 0xD0, (byte) 0x14, (byte) 0xF9, (byte) 0xA8},
                {(byte) 0xC9, (byte) 0xEE, (byte) 0x25, (byte) 0x89},
                {(byte) 0xE1, (byte) 0x3F, (byte) 0x0C, (byte) 0xC8},
                {(byte) 0xB6, (byte) 0x63, (byte) 0x0C, (byte) 0xA6}
        };


        int Nk = key.length / 4;
        int Nr = Nk + 6;

        aes.setNumOfWords(Nk);
        aes.setNumOfRounds(Nr);

        byte[][] expandedKey = aes.expandKey(key);

        for(int i = 0; i<(4*(Nr+1)); i++){
            for(int j=0; j<4; j++){
                assertEquals(table[i][j] & 0xFF, expandedKey[i][j] & 0xFF);
            }
        }
    }
}
