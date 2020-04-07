package com.kul.window.registration;

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

public class RegistrationController implements Initializable {
    private MainController mainController;

    @FXML
    private void goToLoginForm() {
        mainController.setLoginControls();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
