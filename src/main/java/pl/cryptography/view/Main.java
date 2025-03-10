package pl.cryptography.view;

public class Main {
    public static void main(String[] args) {

        byte key[] = {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD,
                (byte) 0xEE, (byte) 0xFF, (byte) 0x11, (byte) 0x22,
                (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66,
                (byte) 0x77, (byte) 0x88, (byte) 0x99, (byte) 0x00};

        AES aes = new AES();

        int Nk = key.length/4;
        int Nr = Nk + 6;

        aes.setNk(Nk);
        aes.setNr(Nr);

        System.out.println("Liczba słów w kluczu:" + Nk + ", Liczba rund:" + Nr);
        for (byte b : key) {
            System.out.printf("%02X ", b);
        }

        System.out.println();
        System.out.println();

        byte[][] expandedKey = aes.expandKey(key);

        for (byte[] row : expandedKey) {
            for (byte b : row) {
                // Wydrukowanie każdego bajtu w formacie HEX (z dużymi literami)
                System.out.printf("%02X ", b);
            }
            System.out.println(); // Przejście do nowej linii po każdej wierszy tablicy
        }
    }
}
