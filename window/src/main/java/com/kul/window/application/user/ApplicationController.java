package com.kul.window.application.user;

import com.kul.window.application.data.GUIUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private final GUIUserInfo user;

    @FXML
    private Label usernameLabel;

    public ApplicationController(GUIUserInfo user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(
                String.format("%s %s", user.username().get(), user.lastName().get())
        );
    }
}
