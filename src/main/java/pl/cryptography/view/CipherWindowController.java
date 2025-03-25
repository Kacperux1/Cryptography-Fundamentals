package pl.cryptography.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.cryptography.logic.AES;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static pl.cryptography.dataAccess.FileManager.readBytesFromFile;
import static pl.cryptography.dataAccess.KeyGenerator.generateKey;

public class CipherWindowController {
    @FXML
    private Button fileChoose;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea cipherArea;
    @FXML
    private TextField fileNamePreview;
    @FXML
    private TextField keyPreview;

    private final AES aes = new AES();

    @FXML
    private TextField decipherNamePreview;
    private File currentFile;
    private File currentDecipherFile;
    private byte[] currentKey;

    private byte[] currentCiphered;


    public void initialize() {
        fileNamePreview.setEditable(false);
    }

    public void selectFile(ActionEvent actionEvent) {
        currentFile = selectFile();
        fileNamePreview.setText(currentFile.getAbsolutePath());
    }

    public void saveTextToFile(ActionEvent actionEvent) {
        File selectedFile = selectFileToSave();;
        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write(textArea.getText());
            showAlert("Plik zapisany: " + selectedFile.getAbsolutePath());
        } catch (IOException e) {
            showAlert("Błąd zapisu do pliku.");
        }
    }

    private File selectFileToSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );
        Stage stage = (Stage) fileChoose.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);
        if(selectedFile == null) {
            showAlert("NIe wybrano pliku!");
        }
        return selectedFile;
    }

    public void saveCipherToFile(ActionEvent actionEvent) {
        File selectedFile = selectFileToSave();
        try {
            assert selectedFile != null;
            try (FileWriter writer = new FileWriter(selectedFile)) {
                writer.write(cipherArea.getText());
                showAlert("Plik zapisany: " + selectedFile.getAbsolutePath());
            }
        } catch (IOException e) {
            showAlert("Błąd zapisu do pliku.");
        }
    }

    public void readFromFile(ActionEvent actionEvent) {
        String result = "";
        try {
            result = new String(readBytesFromFile(currentFile));
            textArea.setText(result);
        } catch (IOException e) {
            showAlert("Błąd zapisu do pliku.");
        }
        catch (NullPointerException e) {
            showAlert("Wybierz plik!");
        }
    }

    public void clearText(ActionEvent actionEvent) {
        textArea.setText("");
    }

    public void clearCipher(ActionEvent actionEvent) {
        cipherArea.setText("");
    }

    public void cipher(ActionEvent actionEvent) {
        byte[] bytesToCipher = textArea.getText().getBytes(StandardCharsets.UTF_8);
        if (currentKey == null) {
            getKey(16);
        }
        byte[] cipheredBytes = new byte[bytesToCipher.length];
        cipheredBytes = aes.encode(bytesToCipher, currentKey);
        cipherArea.setText(Base64.getEncoder().encodeToString(cipheredBytes));
        currentCiphered = cipheredBytes;

    }

    public void decipher(ActionEvent actionEvent) {

        byte[] decipheredBytes;
        decipheredBytes = aes.decode(currentCiphered, currentKey);
        textArea.setText(new String(decipheredBytes));
    }

    private void getKey(int size) {
        String result = "";
        currentKey = generateKey(size);
        result = new String(currentKey);
        keyPreview.setText(result);
    }

    public void generateKey16Bytes(ActionEvent actionEvent) {
        getKey(16);
    }

    public void generateKey24Bytes(ActionEvent actionEvent) {
        getKey(24);
    }

    public void generateKey32Bytes(ActionEvent actionEvent) {
        getKey(32);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Błąd!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void dataFromKeyboardOption(ActionEvent actionEvent) {
        textArea.setEditable(true);
        cipherArea.setEditable(true);
        textArea.setText("");
        cipherArea.setText("");
    }

    public void dataFromFileOption(ActionEvent actionEvent) {
        textArea.setEditable(false);
        cipherArea.setEditable(false);
        textArea.setText("");
        cipherArea.setText("");
    }

    public void selectDecipherFile(ActionEvent actionEvent) {
        File tempFile = selectFile();
        try {
            if (tempFile == null) {
                throw new NullPointerException("Nie wybrano pliku.");
            }
        }
        catch(NullPointerException e) {
            showAlert(e.getMessage());
            return;
        }
        currentDecipherFile = tempFile;
        decipherNamePreview.setText(currentDecipherFile.getAbsolutePath());
    }

    private File selectFile() {
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
        return selectedFile;
    }

    public void readCipheredData(ActionEvent actionEvent) {
        String result = "";
        try {
            result = new String(readBytesFromFile(currentDecipherFile));
            cipherArea.setText(result);
        } catch (IOException e) {
            showAlert("Błąd odczytu pliku.");
        }
        catch (NullPointerException e) {
            showAlert("Wybierz plik!");
        }
    }
}
