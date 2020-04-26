package com.kul.window.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.kul.window.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class LoginController implements Initializable {

    private final ViewModel mainViewModel;
    private final LoginViewModel loginViewModel;

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Text accountLockError;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton registerButton;

    public LoginController(ViewModel mainViewModel, LoginViewModel loginViewModel) {
        this.mainViewModel = mainViewModel;
        this.loginViewModel = loginViewModel;
    }

    @FXML
    void goToSignUpPanelFromLogin() {
        mainViewModel.openRegistrationMenu();
    }

    @FXML
    void forgotPassword() {
        // TODO: Add forgot password functionality
    }

    @FXML
    void performSignIn() {
        loginViewModel.signIn(usernameField.getText(), passwordField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        initializeBindings();
    }

    private void initializeBindings() {
        usernameError.visibleProperty()
                .bind(loginViewModel.usernameErrorProperty());
        passwordError.visibleProperty()
                .bind(loginViewModel.passwordErrorProperty());
        accountLockError.visibleProperty()
                .bind(loginViewModel.accountLockedProperty());
    }

    private void initializeButtons() {
        loginButton.disableProperty().bind(loginViewModel.actionsLockedProperty());
        registerButton.disableProperty().bind(loginViewModel.actionsLockedProperty());
    }
}
