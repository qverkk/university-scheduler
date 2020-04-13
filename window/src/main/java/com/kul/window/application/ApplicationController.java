package com.kul.window.application;

import com.kul.api.data.Constants;
import com.kul.api.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = Constants.loggedInUser;
        usernameLabel.setText(
                String.format("%s %s", user.getFirstName(), user.getLastName())
        );
    }
}
