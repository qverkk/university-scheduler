package com.kul.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/kul/window/panes/MainWindow.fxml"));
        primaryStage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        primaryStage.setTitle("Login panel");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
