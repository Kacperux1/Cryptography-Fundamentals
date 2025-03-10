package pl.cryptography.view;

public class AES {
    final private int Nb = 4; //Nb - liczba kolumn stanu, jak długa
    private int Nr, Nk; //Nr - Liczba rund  //Nk - liczba słów w kluczu
    private byte[][] mainKey; //Klucz główny (czyli już po expansji)

    public void setNr(int nr) {
        Nr = nr;
    }

    public void setNk(int nk) {
        Nk = nk;
    }

    private byte[][] expandKey(byte[] key){
        byte[][] expandedKey = new byte[4][];


        return null;
    }

    public byte[][] generateKey(byte[] key)
    {
        byte[][] temp = new byte[Nb * (Nr+1)][4];
        int i = 0;
        int j =0;
        while (i < Nk)
        {
            temp[i][0] = key[j];
            temp[i][1] = key[j++];
            temp[i][2] = key[j++];
            temp[i][3] = key[j++];
            i++;
            j++;
        }
        return temp;
    }
}
