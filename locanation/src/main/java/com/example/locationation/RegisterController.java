package com.example.locationation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    @FXML
    private Label backToLoginLabel; // Matches fx:id="backToLoginLabel"

    @FXML
    private void goToLoginPage() throws IOException {
        // Load hello-view.fxml
        Parent loginPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene loginPageScene = new Scene(loginPage);
        // Get the current stage
        Stage stage = (Stage) backToLoginLabel.getScene().getWindow();
        // Set the new scene
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void onbuttonclick(ActionEvent actionEvent) {
    }
}