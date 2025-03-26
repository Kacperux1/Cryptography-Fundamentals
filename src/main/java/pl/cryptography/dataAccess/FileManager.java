package pl.cryptography.dataAccess;

import java.io.*;

public class FileManager {


    public static byte[] readBytesFromFile(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(result);
        }
        return result;
    }

    public static void writeBytesToFile(byte[] data, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }
}
