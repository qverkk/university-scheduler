package com.kul.window.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 04.04.2020
 **/

public class SceneController {

    public void setLoginScene() throws IOException {
        showScene("/com/kul/window/panes/DocumentLoginForm.fxml",
                "Login panel", 700, 400);
    }

    public void setRegistrationScene() throws IOException {
        showScene("/com/kul/window/panes/DocumentRegistrationForm.fxml",
                "Registration panel", 700, 400);
    }

    private void showScene(String fileName, String windowName, int width, int height) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        primaryStage.setTitle(windowName);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }
}
