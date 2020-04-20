package com.kul.window.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.domain.user.authorization.ExistingUser;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserInfo;
import com.kul.window.MainController;
import com.kul.window.application.ApplicationController;
import feign.FeignException;
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
        System.out.println("Forgot password");
    }

    @FXML
    void performSignIn() {
        try {
            resetErrorFields();
            final ExistingUser existingUser = new ExistingUser(
                    usernameField.getText(),
                    passwordField.getText()
            );
            final ExistingUserToken existingUserToken = userAuthorizationFacade.authenticate(existingUser);
            final UserInfo userInfo = userAuthorizationFacade.loginWithToken(existingUserToken);
            openApplication(userInfo);

            Stage stage = (Stage) passwordError.getScene().getWindow();
            stage.close();
            mainController.removeNodes();
        } catch (FeignException.NotFound error) {
            usernameError.setVisible(true);
            usernameError.setText("User doesn't exist");
        } catch (FeignException.Unauthorized unauthorized) {
            passwordError.setVisible(true);
            passwordError.setText("Password isn't correct");
        } catch (FeignException.Forbidden forbidden) {
            accountLockError.setVisible(true);
            accountLockError.setText("Account is locked. Please contact an admin.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void openApplication(UserInfo userInfo) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kul/window/panes/MainApplication.fxml"));
        loader.setController(new ApplicationController(userInfo));

        Parent root = loader.load();
        stage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        stage.setTitle("KUL Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
