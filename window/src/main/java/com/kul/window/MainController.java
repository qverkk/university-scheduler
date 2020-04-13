package com.kul.window;

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

    @FXML
    private Pane controlsPane;

    private Node loginNode;
    private Node registerNode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoginControls();
    }

    public void setLoginControls() {
        setControls("/com/kul/window/panes/LoginForm.fxml", loginNode);
    }

    public void setRegisterControls() {
        setControls("/com/kul/window/panes/RegisterForm.fxml", registerNode);
    }

    private void setControls(String fxmlPath, Node node) {
        try {
            controlsPane.getChildren().clear();
            if (node == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                node = loader.load();
                Object controller = loader.getController();
                if ("/com/kul/window/panes/LoginForm.fxml".equals(fxmlPath)) {
                    ((LoginController) controller).setMainController(this);
                    loginNode = node;
                } else {
                    ((RegistrationController) controller).setMainController(this);
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
