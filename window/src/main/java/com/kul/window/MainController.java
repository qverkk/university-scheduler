package com.kul.window;

import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.domain.user.registration.UserRegistration;
import com.kul.window.login.LoginController;
import com.kul.window.registration.RegistrationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final LoginController loginController;
    private final RegistrationController registrationController;

    @FXML
    private Pane controlsPane;

    private Node loginNode;
    private Node registerNode;

    public MainController(UserRegistration userRegistration, UserAuthorizationFacade userAuthorizationFacade) {
        this.loginController = new LoginController(this, userAuthorizationFacade);
        this.registrationController = new RegistrationController(this, userRegistration);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoginControls();
    }

    public void setLoginControls() {
        setControls("/com/kul/window/panes/LoginForm.fxml", loginNode, loginController);
    }

    public void setRegisterControls() {
        setControls("/com/kul/window/panes/RegisterForm.fxml", registerNode, registrationController);
    }

    private void setControls(String fxmlPath, Node node, Initializable controller) {
        try {
            controlsPane.getChildren().clear();
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
            controlsPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeNodes() {
        registerNode = null;
        loginNode = null;
    }
}
