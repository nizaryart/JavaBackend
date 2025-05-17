package com.example.locationation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button login_button;
    @FXML
    private Button register_button;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onbuttonclick() {
        System.out.println(password.getText()); // Prints password field content
    }

    @FXML
    public void goToRegisterPage() throws IOException {
        // Load register.fxml
        Parent registerPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
        Scene registerPageScene = new Scene(registerPage);

        // Get the current stage
        Stage stage = (Stage) register_button.getScene().getWindow();

        // Set the new scene
        stage.setScene(registerPageScene);
        stage.show();
    }
}