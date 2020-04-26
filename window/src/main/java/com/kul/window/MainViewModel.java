package com.kul.window;

import com.kul.window.login.LoginController;
import com.kul.window.login.LoginViewModel;
import com.kul.window.registration.RegistrationController;
import com.kul.window.registration.RegistrationViewModel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.io.IOException;

public class MainViewModel implements ViewModel {

    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final MainController mainController;

    private Node loginNode;
    private Node registerNode;

    public MainViewModel(
            LoginViewModel loginViewModel,
            RegistrationViewModel registrationViewModel,
            MainController mainController
    ) {
        this.mainController = mainController;
        this.loginController = new LoginController(
                this,
                loginViewModel
        );
        this.registrationController = new RegistrationController(
                registrationViewModel,
                this
        );
    }

    @Override
    public void openLoginMenu() {
        getControls("/com/kul/window/panes/LoginForm.fxml", loginNode, loginController);
    }

    @Override
    public void openRegistrationMenu() {
        getControls("/com/kul/window/panes/RegisterForm.fxml", registerNode, registrationController);
    }

    private void getControls(String fxmlPath, Node node, Initializable controller) {
        try {
            mainController.clearMenu();
            if (node == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                loader.setController(controller);
                node = loader.load();
                if ("/com/kul/window/panes/LoginForm.fxml".equals(fxmlPath)) {
                    loginNode = node;
                } else {
                    registerNode = node;
                }
            }
            mainController.addMenu(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
