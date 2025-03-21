package pl.cryptography.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.cryptography.dataAccess.FileManager;
import pl.cryptography.dataAccess.KeyGenerator;
import pl.cryptography.logic.AES;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import static pl.cryptography.dataAccess.FileManager.readBytesFromFile;
import static pl.cryptography.dataAccess.KeyGenerator.generateKey16;

public class CipherWindowController {

    public Button fileChoose;
    public TextArea textArea;
    public TextArea cipherArea;
    public TextField fileNamePreview;
    public TextField keyPreview;

    private final AES aes = new AES();
    private File currentFile;
    private byte[] currentKey;

    private byte [] currentCiphered;


    public void initialize() {
        fileNamePreview.setEditable(false);
    }
    public void selectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );
        Stage stage = (Stage) fileChoose.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Wybrano plik: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Nie wybrano pliku.");
        }
        currentFile = selectedFile;
        fileNamePreview.setText(currentFile.getAbsolutePath());
    }

    public void saveTextToFile(ActionEvent actionEvent) {

    }

    public void saveCipherToFile(ActionEvent actionEvent) {
    }

    public void readFromFile(ActionEvent actionEvent) {
        String result="";
        try {
            result=new String(readBytesFromFile(currentFile));
            textArea.setText(result);
        } catch (IOException e) {
            System.out.print("dupa");
        }
    }

    public void clearText(ActionEvent actionEvent) {
        textArea.setText("");
    }

    public void clearCipher(ActionEvent actionEvent) {
        cipherArea.setText("");
    }

    public void cipher(ActionEvent actionEvent) {
        try {
            byte[] bytesToCipher = readBytesFromFile(currentFile);
            if(currentKey==null){
                getKey16();
            }
            byte[] cipheredBytes = new byte[bytesToCipher.length];
            cipheredBytes = aes.encode(bytesToCipher, currentKey);
            cipherArea.setText(Base64.getEncoder().encodeToString(cipheredBytes));
            currentCiphered = cipheredBytes;
        } catch (IOException e) {
            System.out.print("dupa");
        }

    }

    public void decipher(ActionEvent actionEvent) {

            byte[] decipheredBytes;
            decipheredBytes = aes.decode(currentCiphered, currentKey);
            textArea.setText(new String(decipheredBytes));
    }

    private void getKey16() {
        String result="";
        currentKey = generateKey16();
        result = new String(currentKey);
        keyPreview.setText(result);
    }

    public void generateKey16Bytes(ActionEvent actionEvent) {
        getKey16();
    }
}
