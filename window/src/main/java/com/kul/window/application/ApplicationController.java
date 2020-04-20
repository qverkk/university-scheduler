package com.kul.window.application;

import com.kul.api.domain.user.authorization.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private final UserInfo user;

    @FXML
    private Label usernameLabel;

    public ApplicationController(UserInfo user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(
                String.format("%s %s", user.getFirstName(), user.getLastName())
        );
    }
}
