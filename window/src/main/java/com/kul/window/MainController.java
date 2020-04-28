package com.kul.window;

import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.domain.user.registration.UserRegistration;
import com.kul.window.application.helpers.ApplicationWindowManager;
import com.kul.window.async.ExecutorsFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final MainViewModel mainViewModel;

    @FXML
    private Pane controlsPane;

    public MainController(
            ApplicationWindowManager applicationWindowManager,
            UserAuthorizationFacade userAuthorizationFacade,
            ExecutorsFactory preconfiguredExecutors,
            UserRegistration userRegistration
    ) {
        this.mainViewModel = new MainViewModel(
                applicationWindowManager,
                userAuthorizationFacade,
                preconfiguredExecutors,
                userRegistration
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainViewModel.nodeProperty().addListener(((observable, oldValue, newValue) -> {
            clearMenu();
            addMenu(newValue);
        }));

        clearMenu();
        mainViewModel.openLoginMenu();
    }

    public void clearMenu() {
        this.controlsPane.getChildren().clear();
    }

    public void addMenu(Node node) {
        this.controlsPane.getChildren().add(node);
    }
}
