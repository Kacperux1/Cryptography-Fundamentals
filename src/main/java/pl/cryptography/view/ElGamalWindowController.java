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

public class ElGamalWindowController {
    @FXML
    private TextField primeNumber;
    @FXML
    private TextField generator;
    @FXML
    private TextField privateKey;
    @FXML
    private TextField publicKey;
}
