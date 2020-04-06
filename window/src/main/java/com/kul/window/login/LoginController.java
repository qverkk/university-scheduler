package com.kul.window.login;

import com.jfoenix.controls.JFXButton;
import com.kul.window.scene.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class LoginController extends SceneController implements Initializable {

    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton registrationBtn;

    @FXML
    public void goToSignUpPanelFromLogin() throws IOException {
        setRegistrationScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
