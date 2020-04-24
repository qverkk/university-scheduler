package com.kul.window.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.kul.api.adapter.admin.external.ManagementEndpointClient;
import com.kul.api.adapter.admin.external.ManagementEndpointClientFactory;
import com.kul.api.adapter.admin.management.ManagementUserRepositoryFacade;
import com.kul.api.adapter.user.authorization.UserAccountDisabledException;
import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.adapter.user.authorization.UserLoginAccountDoesntExistException;
import com.kul.api.adapter.user.authorization.UserLoginWrongPasswordException;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.ExistingUser;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserInfo;
import com.kul.api.model.AuthorityEnum;
import com.kul.window.MainController;
import com.kul.window.application.admin.AdminController;
import com.kul.window.application.data.GUIUserInfo;
import com.kul.window.application.user.ApplicationController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class LoginController implements Initializable {

    private final MainController mainController;
    private final UserAuthorizationFacade userAuthorizationFacade;

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

    private boolean actionsLocked = false;

    public LoginController(MainController mainController, UserAuthorizationFacade userAuthorizationFacade) {
        this.mainController = mainController;
        this.userAuthorizationFacade = userAuthorizationFacade;
    }

    @FXML
    void goToSignUpPanelFromLogin() {
        if (actionsLocked) {
            return;
        }
        mainController.setRegisterControls();
    }

    @FXML
    void forgotPassword() {
        // TODO: Add forgot password functionality
    }

    @FXML
    void performSignIn() {
        if (actionsLocked) {
            return;
        }
        resetErrorFields();
        final ExistingUser existingUser = new ExistingUser(
                usernameField.getText(),
                passwordField.getText()
        );
        actionsLocked = true;
        new Thread(() -> {
            try {
                final ExistingUserToken existingUserToken = userAuthorizationFacade.authenticate(existingUser);
                final UserInfo userInfo = userAuthorizationFacade.loginWithToken(existingUserToken);

                final GUIUserInfo guiUserInfo = new GUIUserInfo(
                        0L,
                        userInfo.getFirstName(),
                        userInfo.getLastName(),
                        userInfo.getUsername(),
                        true,
                        userInfo.getAuthority()
                );

                Platform.runLater(() -> {
                    try {
                        if (userInfo.getAuthority() == AuthorityEnum.ADMIN) {
                            openAdminPanel(guiUserInfo, existingUserToken);
                        } else {
                            openApplication(guiUserInfo);
                        }

                        Stage stage = (Stage) passwordError.getScene().getWindow();
                        stage.close();
                        mainController.removeNodes();
                    } catch (IOException ignored) {
                    }
                });
            } catch (UserLoginAccountDoesntExistException accountDoesntExistException) {
                usernameError.setVisible(true);
                usernameError.setText("User doesn't exist");
            } catch (UserLoginWrongPasswordException passwordException) {
                passwordError.setVisible(true);
                passwordError.setText("Password isn't correct");
            } catch (UserAccountDisabledException userAccountDisabledException) {
                accountLockError.setVisible(true);
                accountLockError.setText("Account is locked. Please contact an admin.");
            }
            actionsLocked = false;
        }).start();
    }

    private void resetErrorFields() {
        usernameError.setVisible(false);
        usernameError.setText("");
        passwordError.setVisible(false);
        passwordError.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void openApplication(GUIUserInfo userInfo) throws IOException {
        changeWindow(
                "/com/kul/window/panes/MainApplication.fxml",
                "KUL Scheduler",
                new ApplicationController(userInfo)
        );
    }


    private void openAdminPanel(GUIUserInfo userInfo, ExistingUserToken existingUserToken) throws IOException {
        ManagementEndpointClient endpointClient = new ManagementEndpointClientFactory(existingUserToken).create();

        UserManagement userManagement = new UserManagement(
                new ManagementUserRepositoryFacade(endpointClient)
        );

        changeWindow(
                "/com/kul/window/panes/AdminWindow.fxml",
                "KUL Scheduler Admin panel",
                new AdminController(userInfo, userManagement)
        );
    }

    private void changeWindow(String resource, String windowTitle, Initializable controller) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        loader.setController(controller);

        Parent root = loader.load();
        stage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
