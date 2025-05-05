package pl.cryptography.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public Button symEncode;
    public Button asymEncode;

    public void enterCipherWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) symEncode.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(CryptographyFundamentals.class.getResource("/pl/cryptography/CiperWindow.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 1024, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Podstawy Kryptografii");
        stage.setScene(scene);
        stage.show();
    }


    public void enterElGamalWindow(ActionEvent actionEvent) {
        System.out.println("Kliknięto przycisk ElGamal!");
        Stage stage = (Stage) asymEncode.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(CryptographyFundamentals.class.getResource("/pl/cryptography/CiperWindowElGamal.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 1024, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Podstawy Kryptografii");
        stage.setScene(scene);
        stage.show();
    }
}
