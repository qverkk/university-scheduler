package com.kul.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/kul/window/panes/DocumentLoginForm.fxml"));
        primaryStage.setTitle("Registration panel");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
}
