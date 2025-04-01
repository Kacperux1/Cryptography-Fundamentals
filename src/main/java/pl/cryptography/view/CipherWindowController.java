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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static pl.cryptography.dataAccess.FileManager.readBytesFromFile;
import static pl.cryptography.dataAccess.FileManager.writeBytesToFile;
import static pl.cryptography.logic.KeyGenerator.generateKey;

public class CipherWindowController {
    @FXML
    private Button textSaver;
    @FXML
    private Button fileReader;
    @FXML
    private Button cipherReader;
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
    private byte[] currentPlainData;
    private byte[] currentCiphered;


    public void initialize() {
        fileNamePreview.setEditable(false);
        decipherNamePreview.setEditable(false);
        textArea.setEditable(false);
        cipherArea.setEditable(false);
        fileReader.setDisable(true);
        cipherReader.setDisable(true);
    }

    public void selectFile(ActionEvent actionEvent) {
       File temp;
       try {
           temp = selectFile();
       } catch (NullPointerException e) {
           showAlert(e.getMessage());
           return;
       }
        currentFile = temp;
        fileNamePreview.setText(currentFile.getAbsolutePath());
    }

    public void saveTextToFile(ActionEvent actionEvent) {
        File selectedFile = selectFileToSave();
        if (selectedFile == null) {
            showAlert("Nie wybrano pliku!");
            return;
        }
        try  {
            writeBytesToFile(currentPlainData, selectedFile);
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
        byte [] result;
        try {
            result = readBytesFromFile(currentFile);
            currentPlainData = result;
            textArea.setText(new String(result));

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
        if(currentKey.length >0 && (currentKey.length !=16 && currentKey.length!=24
                && currentKey.length!=32)) {
            showAlert("klucz ma nieprawidłową długość!");
            return;
        }
        if(textArea.getText().isEmpty()) {
            showAlert("Brak tekstu!");
            return;
        }
        byte[] bytesToCipher = textArea.getText().getBytes(StandardCharsets.UTF_8);
        if (currentKey == null) {
            getKey(16);
        }
        currentKey = keyPreview.getText().getBytes();
        byte[] cipheredBytes;
        try {
            cipheredBytes = aes.encode(bytesToCipher, currentKey);
            cipherArea.setText(Base64.getEncoder().encodeToString(cipheredBytes));
            currentCiphered = cipheredBytes;
        }
        catch (Exception e) {
            showAlert("Nie udało się zaszyfrować!");
        }

    }

    public void decipher(ActionEvent actionEvent) {
        if(cipherArea.getText().isEmpty()) {
            showAlert("Brak tekstu!");
            return;
        }
        byte[] decipheredBytes;
        try {
            decipheredBytes = aes.decode(currentCiphered, currentKey);
            textArea.setText(new String(decipheredBytes));
        } catch (Exception e){
            showAlert("Nie udało sie odszyfrować (sprawdź, czy tym samym kluczem zaszyfrowano wiadomość.)");
        }

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
        fileReader.setDisable(true);
        cipherReader.setDisable(true);
    }

    public void dataFromFileOption(ActionEvent actionEvent) {
        textArea.setEditable(false);
        cipherArea.setEditable(false);
        textArea.setText("");
        cipherArea.setText("");
        fileReader.setDisable(false);
        cipherReader.setDisable(false);
    }

    public void selectDecipherFile(ActionEvent actionEvent) {
        File tempFile;
        try {
            tempFile = selectFile();
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
            throw new NullPointerException("nie wybrano pliku");
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

    public void clearKey(ActionEvent actionEvent) {
        keyPreview.setText("");
        currentKey = null;
    }
}
