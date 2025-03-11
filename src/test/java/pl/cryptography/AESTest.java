package pl.cryptography;

import org.junit.jupiter.api.Test;
import pl.cryptography.view.AES;

import static org.junit.jupiter.api.Assertions.*;

public class AESTest {

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

        AES aes = new AES();
        byte[][] after = aes.mixColumns(origin);

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                assertEquals((anticipated[i][j] & 0xFF), (after[i][j]) & 0xFF);
            }
        }

    }

    @Test
    public void fMulTest(){
        AES aes = new AES();
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

        AES aes = new AES();
        blok = aes.shiftRows(blok);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                assertEquals((blok[i][j] & 0xFF), (blok2[i][j] &0xFF));
            }
        }
    }

    @Test
    public void subRowTest() {
        AES aes = new AES();
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
        AES aes = new AES();
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
        byte[] key = {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD,
                (byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22,
                (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66,
                (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00};

        byte[][] table = {
                {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD},
                {(byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22},
                {(byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66},
                {(byte) 0x88, (byte) 0x99, (byte) 0x00, (byte) 0x77},
                {(byte) 0x6F, (byte) 0x55, (byte) 0xAF, (byte) 0x28},
                {(byte) 0x81, (byte) 0xAA, (byte) 0xBE, (byte) 0x0A},
                {(byte) 0xB2, (byte) 0xEE, (byte) 0xEB, (byte) 0x6C},
                {(byte) 0x77, (byte) 0xEB, (byte) 0x1B, (byte) 0x3A},
                {(byte) 0x98, (byte) 0xBC, (byte) 0x00, (byte) 0xA8},
                {(byte) 0x19, (byte) 0x16, (byte) 0xBE, (byte) 0xA2},
                {(byte) 0xAB, (byte) 0xF8, (byte) 0x55, (byte) 0xCE},
                {(byte) 0x13, (byte) 0x4E, (byte) 0xF4, (byte) 0xDC},
                {(byte) 0xE1, (byte) 0x93, (byte) 0xBF, (byte) 0x2E},
                {(byte) 0xF8, (byte) 0x85, (byte) 0x01, (byte) 0x8C},
                {(byte) 0x53, (byte) 0x7D, (byte) 0x54, (byte) 0x42},
                {(byte) 0x33, (byte) 0xA0, (byte) 0x9E, (byte) 0x40},
                {(byte) 0x2A, (byte) 0x73, (byte) 0xB4, (byte) 0x27},
                {(byte) 0xD2, (byte) 0xF6, (byte) 0xB5, (byte) 0xAB},
                {(byte) 0x81, (byte) 0x8B, (byte) 0xE1, (byte) 0xE9},
                {(byte) 0x2B, (byte) 0x7F, (byte) 0xA9, (byte) 0xB2},
                {(byte) 0xCB, (byte) 0xA1, (byte) 0x67, (byte) 0x10},
                {(byte) 0x19, (byte) 0x57, (byte) 0xD2, (byte) 0xBB},
                {(byte) 0x98, (byte) 0xDC, (byte) 0x33, (byte) 0x52},
                {(byte) 0xA3, (byte) 0x9A, (byte) 0xE0, (byte) 0xB3},
                {(byte) 0xE1, (byte) 0x19, (byte) 0x86, (byte) 0x7D},
                {(byte) 0xF8, (byte) 0x4E, (byte) 0x54, (byte) 0xC6},
                {(byte) 0x60, (byte) 0x92, (byte) 0x67, (byte) 0x94},
                {(byte) 0x08, (byte) 0x87, (byte) 0x27, (byte) 0xC3},
                {(byte) 0x91, (byte) 0x0E, (byte) 0x4A, (byte) 0x53},
                {(byte) 0x69, (byte) 0x40, (byte) 0x1E, (byte) 0x95},
                {(byte) 0x09, (byte) 0xD2, (byte) 0x79, (byte) 0x01},
                {(byte) 0x55, (byte) 0x5E, (byte) 0xC2, (byte) 0x01},
                {(byte) 0xED, (byte) 0x56, (byte) 0x6F, (byte) 0x2F},
                {(byte) 0x84, (byte) 0x16, (byte) 0x71, (byte) 0xBA},
                {(byte) 0x8D, (byte) 0xC4, (byte) 0x08, (byte) 0xBB},
                {(byte) 0x9A, (byte) 0xCA, (byte) 0xBA, (byte) 0xD8},
                {(byte) 0x4E, (byte) 0x22, (byte) 0x9B, (byte) 0x4E},
                {(byte) 0xCA, (byte) 0x34, (byte) 0xEA, (byte) 0xF4},
                {(byte) 0x47, (byte) 0xF0, (byte) 0xE2, (byte) 0x4F},
                {(byte) 0x3A, (byte) 0x58, (byte) 0x97, (byte) 0xDD},
                {(byte) 0xF8, (byte) 0x48, (byte) 0x13, (byte) 0x8F},
                {(byte) 0x32, (byte) 0x7C, (byte) 0xF9, (byte) 0x7B},
                {(byte) 0x75, (byte) 0x8C, (byte) 0x1B, (byte) 0x34},
                {(byte) 0x4F, (byte) 0xD4, (byte) 0x8C, (byte) 0xE9}
        };

        AES aes = new AES();

        int Nk = key.length / 4;
        int Nr = Nk + 6;

        aes.setNk(Nk);
        aes.setNr(Nr);

        byte[][] expandedKey = aes.expandKey(key);

        for(int i = 0; i<(4*(Nr+1)); i++){
            for(int j=0; j<4; j++){
                assertEquals(table[i][j] & 0xFF, expandedKey[i][j] & 0xFF);
            }
        }
    }
}
