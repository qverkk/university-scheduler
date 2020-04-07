package com.kul.window.login;

import com.jfoenix.controls.JFXButton;
import com.kul.window.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class LoginController implements Initializable {

    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton registrationBtn;

    private MainController mainController;

    @FXML
    public void goToSignUpPanelFromLogin() {
        mainController.setRegisterControls();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
