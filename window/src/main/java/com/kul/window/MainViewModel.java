package com.kul.window;

import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.domain.user.registration.UserRegistration;
import com.kul.window.application.helpers.ApplicationWindowManager;
import com.kul.window.async.ExecutorsFactory;
import com.kul.window.login.LoginController;
import com.kul.window.login.LoginViewModel;
import com.kul.window.registration.RegistrationController;
import com.kul.window.registration.RegistrationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.io.IOException;

public class MainViewModel implements ViewModel {

    private final LoginController loginController;
    private final RegistrationController registrationController;
    private final ObjectProperty<Node> nodeObject = new SimpleObjectProperty<Node>();

    private Node loginNode;
    private Node registerNode;

    public MainViewModel(
            ApplicationWindowManager applicationWindowManager,
            UserAuthorizationFacade userAuthorizationFacade,
            ExecutorsFactory preconfiguredExecutors,
            UserRegistration userRegistration
    ) {
        this.loginController = new LoginController(
                new LoginViewModel(
                        this,
                        applicationWindowManager,
                        userAuthorizationFacade,
                        preconfiguredExecutors
                )
        );
        this.registrationController = new RegistrationController(
                new RegistrationViewModel(
                        this,
                        userRegistration,
                        preconfiguredExecutors
                )
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
            nodeObject.setValue(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectProperty<Node> nodeProperty() {
        return nodeObject;
    }
}
