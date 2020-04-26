package com.kul.window.login;

import com.jfoenix.controls.JFXButton;
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
import com.kul.window.application.data.UserAndTokenInfo;
import com.kul.window.application.data.UserInfoViewModel;
import com.kul.window.application.user.ApplicationController;
import com.kul.window.async.PreconfiguredExecutors;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final BooleanProperty actionsLocked = new SimpleBooleanProperty(false);
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

    public LoginController(MainController mainController, UserAuthorizationFacade userAuthorizationFacade) {
        this.mainController = mainController;
        this.userAuthorizationFacade = userAuthorizationFacade;
    }

    @FXML
    void goToSignUpPanelFromLogin() {
        mainController.setRegisterControls();
    }

    @FXML
    void forgotPassword() {
        // TODO: Add forgot password functionality
    }

    @FXML
    void performSignIn() {
        resetErrorFields();
        final ExistingUser existingUser = new ExistingUser(
                usernameField.getText(),
                passwordField.getText()
        );
        actionsLocked.set(true);
        Scheduler loginUserScheduler = Schedulers.from(
                PreconfiguredExecutors.noQueueNamedSingleThreadExecutor("login-user-%d")
        );
        Single
                .fromCallable(() -> userAuthorizationFacade.authenticate(existingUser))
                .subscribeOn(loginUserScheduler)
                .flatMap(token -> Single.fromCallable(() -> {
                    final UserInfo userInfo = userAuthorizationFacade.loginWithToken(token);
                    return new UserAndTokenInfo(token, userInfo);
                }))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(userAndTokenInfo -> {
                    final UserInfo userInfo = userAndTokenInfo.getUserInfo();
                    final ExistingUserToken userToken = userAndTokenInfo.getUserToken();

                    final UserInfoViewModel guiUserInfo = new UserInfoViewModel(
                            0L,
                            userInfo.getFirstName(),
                            userInfo.getLastName(),
                            userInfo.getUsername(),
                            true,
                            userInfo.getAuthority()
                    );

                    if (userInfo.getAuthority() == AuthorityEnum.ADMIN) {
                        openAdminPanel(guiUserInfo, userToken);
                    } else {
                        openApplication(guiUserInfo);
                    }

                    Stage stage = (Stage) passwordError.getScene().getWindow();
                    stage.close();
                    mainController.removeNodes();
                    actionsLocked.set(false);
                }, error -> {
                    if (error instanceof UserLoginAccountDoesntExistException) {
                        usernameError.setVisible(true);
                        usernameError.setText("User doesn't exist");
                    } else if (error instanceof UserLoginWrongPasswordException) {
                        passwordError.setVisible(true);
                        passwordError.setText("Password isn't correct");
                    } else if (error instanceof UserAccountDisabledException) {
                        accountLockError.setVisible(true);
                        accountLockError.setText("Account is locked. Please contact an admin.");
                    }
                    actionsLocked.set(false);
                    loginUserScheduler.shutdown();
                });
    }

    private void resetErrorFields() {
        usernameError.setVisible(false);
        usernameError.setText("");
        passwordError.setVisible(false);
        passwordError.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
    }

    private void initializeButtons() {
        loginButton.disableProperty().bind(actionsLocked);
        registerButton.disableProperty().bind(actionsLocked);
    }

    private void openApplication(UserInfoViewModel userInfo) throws IOException {
        changeWindow(
                "/com/kul/window/panes/MainApplication.fxml",
                "KUL Scheduler",
                new ApplicationController(userInfo)
        );
    }


    private void openAdminPanel(UserInfoViewModel userInfo, ExistingUserToken existingUserToken) throws IOException {
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
