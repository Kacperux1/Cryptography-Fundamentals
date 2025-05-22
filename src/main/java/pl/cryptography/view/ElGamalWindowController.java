package pl.cryptography.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.cryptography.DigitalSignature.ElGamal;
import pl.cryptography.DigitalSignature.Signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static pl.cryptography.dataAccess.FileManager.readBytesFromFile;
import static pl.cryptography.dataAccess.FileManager.writeBytesToFile;
import static pl.cryptography.logic.KeyGenerator.generateKey;

public class ElGamalWindowController {
    @FXML
    private TextField primeNumber;
    @FXML
    private TextField generator;
    @FXML
    private TextField privateKey;
    @FXML
    private TextField publicKey;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea cipherArea;
    @FXML
    private TextField fileNamePreview;
    @FXML
    private Button fileChoose;
    @FXML
    private Button fileReader;
    @FXML
    private Button cipherReader;


    @FXML
    private TextField decipherNamePreview;
    private File currentFile;
    private File currentDecipherFile;
    private byte[] currentKey;
    private byte[] currentPlainData;
    private byte[] currentCiphered;
    private ElGamal elgamal;

    public void initialize() {
        publicKey.setEditable(false);
        fileNamePreview.setEditable(false);
        decipherNamePreview.setEditable(false);
        cipherArea.setEditable(false);
        fileReader.setDisable(true);
        cipherReader.setDisable(true);
        primeNumber.setEditable(false);
        generator.setEditable(false);
        privateKey.setEditable(false);
        publicKey.setEditable(false);
        elgamal = new ElGamal();
    }

    public void verify() {
        BigInteger number = new BigInteger("0");

        if (cipherArea.getText().isEmpty()) {
            showAlert("Brak podpisu!");
            return;
        }

        if (textArea.getText().isEmpty()) {
            showAlert("Brak widomości do sprawdzenia!");
            return;
        }

        if (primeNumber.getText().isEmpty()) {
            showAlert("Brak liczby pierwszej p!");
            return;
        }
        try {
            number = new BigInteger(primeNumber.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format liczby pierwszej!");
        }
        elgamal.setP(number);


        if (generator.getText().isEmpty()) {
            showAlert("Brak generatora!");
            return;
        }
        try {
            number = new BigInteger(generator.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format generatora!");
        }
        elgamal.setG(number);


        if (privateKey.getText().isEmpty()) {
            showAlert("Brak klucza prywatnego!");
            return;
        }
        try {
            number = new BigInteger(privateKey.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format klucza prywatnego!");
        }
        elgamal.setA(number);


        if (publicKey.getText().isEmpty()) {
            showAlert("Brak klucza publicznego!");
            return;
        }
        try {
            number = new BigInteger(publicKey.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format klucza publicznego!");
        }
        elgamal.setH(number);

        byte[] bytesToCheck = textArea.getText().getBytes(StandardCharsets.UTF_8);

        String[] parts = cipherArea.getText().split("%");
        if (parts.length != 2) {
            showAlert("Niepoprawny format podpisu!");
            return;
        }
        BigInteger r = new BigInteger(parts[0], 16);
        BigInteger s = new BigInteger(parts[1], 16);

        if (elgamal.verify(bytesToCheck, r, s)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sprawdzono");
            alert.setHeaderText(null);
            alert.setContentText("Podpis Poprawny");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sprawdzono");
            alert.setHeaderText(null);
            alert.setContentText("Podpis NIE Poprawny");
            alert.showAndWait();
        }
    }


    public void sign() {
        BigInteger number = new BigInteger("0");

        if (textArea.getText().isEmpty()) {
            showAlert("Brak widomości do podpisania!");
            return;
        }

        if (primeNumber.getText().isEmpty()) {
            showAlert("Brak liczby pierwszej p!");
            return;
        }
        try {
            number = new BigInteger(primeNumber.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format liczby pierwszej!");
        }
        elgamal.setP(number);


        if (generator.getText().isEmpty()) {
            showAlert("Brak generatora!");
            return;
        }
        try {
            number = new BigInteger(generator.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format generatora!");
        }
        elgamal.setG(number);


        if (privateKey.getText().isEmpty()) {
            showAlert("Brak klucza prywatnego!");
            return;
        }
        try {
            number = new BigInteger(privateKey.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format klucza prywatnego!");
        }
        elgamal.setA(number);


        if (publicKey.getText().isEmpty()) {
            showAlert("Brak klucza publicznego!");
            return;
        }
        try {
            number = new BigInteger(publicKey.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format klucza publicznego!");
        }
        elgamal.setH(number);

        byte[] bytesToCipher = textArea.getText().getBytes(StandardCharsets.UTF_8);
        Signature signature = elgamal.encipher(bytesToCipher);
        String output = signature.getR().toString(16) + "%" + signature.getS().toString(16);
        cipherArea.setText(output);
    }

    private void generatePrimeNumber() {
        BigInteger prime = elgamal.generatePrime();
        elgamal.setP(prime);
        primeNumber.setText(prime.toString(16));
    }

    private void generateGenerator() {
        BigInteger grandom = elgamal.generateG();
        elgamal.setG(grandom);
        generator.setText(grandom.toString(16));
    }

    private void generatePrivateKey() {
        BigInteger key = elgamal.generatePrivateKey();
        elgamal.setA(key);
        privateKey.setText(key.toString(16));
    }

    private void generatePublicKey() {
        BigInteger number = new BigInteger("0");

        if (primeNumber.getText().isEmpty()) {
            showAlert("Brak liczby pierwszej p!");
            return;
        }
        try {
            number = new BigInteger(primeNumber.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format liczby pierwszej!");
        }
        elgamal.setP(number);


        if (generator.getText().isEmpty()) {
            showAlert("Brak generatora!");
            return;
        }
        try {
            number = new BigInteger(generator.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format generatora!");
        }
        elgamal.setG(number);


        if (privateKey.getText().isEmpty()) {
            showAlert("Brak klucza prywatnego!");
            return;
        }
        try {
            number = new BigInteger(privateKey.getText(), 16);
        } catch (NumberFormatException e) {
            showAlert("Nieprawidłowy format klucza prywatnego!");
        }
        elgamal.setA(number);


        BigInteger key = elgamal.generatePublicKey();
        elgamal.setH(key);
        publicKey.setText(key.toString(16));
    }

    public void clearText(ActionEvent actionEvent) {
        textArea.setText("");
    }

    public void clearCipher(ActionEvent actionEvent) {
        cipherArea.setText("");
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
        try {
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
        if (selectedFile == null) {
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
        byte[] result;
        try {
            result = readBytesFromFile(currentFile);
            currentPlainData = result;
            textArea.setText(new String(result));

        } catch (IOException e) {
            showAlert("Błąd zapisu do pliku.");
        } catch (NullPointerException e) {
            showAlert("Wybierz plik!");
        }
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
        textArea.setText("");
        cipherArea.setText("");
        fileReader.setDisable(false);
        cipherReader.setDisable(false);
    }

    public void selectDecipherFile(ActionEvent actionEvent) {
        File tempFile;
        try {
            tempFile = selectFile();
        } catch (NullPointerException e) {
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
        } catch (NullPointerException e) {
            showAlert("Wybierz plik!");
        }
    }

    public void generateKeys(ActionEvent actionEvent) {
        generatePrimeNumber();
        generateGenerator();
        generatePrivateKey();
        generatePublicKey();
    }

    public void clearKeys(ActionEvent actionEvent) {
        primeNumber.setText("");
        generator.setText("");
        privateKey.setText("");
        publicKey.setText("");

    }
}
