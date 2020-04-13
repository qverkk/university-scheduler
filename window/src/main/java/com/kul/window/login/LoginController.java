package com.kul.window.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.kul.api.data.Constants;
import com.kul.api.http.requests.AuthRequest;
import com.kul.api.model.UserLogin;
import com.kul.window.MainController;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;

    private MainController mainController;

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
        AuthRequest authentication = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthRequest.class, Constants.HOST_URL);
        try {
            resetErrorFields();
            String token = authentication.generateToken(
                    new UserLogin(
                            usernameField.getText(),
                            passwordField.getText()
                    )
            );
            Constants.loggedInUser = authentication.login(token);
            openApplication();
            Stage stage = (Stage) passwordError.getScene().getWindow();
            stage.close();
            mainController.removeNodes();
        } catch (FeignException.NotFound error) {
            usernameError.setVisible(true);
            usernameError.setText("User doesn't exist");
        } catch (FeignException.Unauthorized unauthorized) {
            passwordError.setVisible(true);
            passwordError.setText("Password isn't correct");
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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void openApplication() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/kul/window/panes/MainApplication.fxml"));
        stage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        stage.setTitle("KUL Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
