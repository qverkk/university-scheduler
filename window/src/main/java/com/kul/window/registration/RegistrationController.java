package com.kul.window.registration;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.kul.api.data.Constants;
import com.kul.api.http.requests.AuthRequest;
import com.kul.api.model.AuthorityEnum;
import com.kul.api.model.User;
import com.kul.api.validators.MatchingValidator;
import com.kul.api.validators.PasswordValidator;
import com.kul.api.validators.UserDetailsValidator;
import com.kul.window.MainController;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class RegistrationController implements Initializable {
    private MainController mainController;

    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField repeatUsernameField;
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXTextField repeatPasswordField;

    private boolean canRegister() {
        return firstnameField.validate() &&
                lastnameField.validate() &&
                usernameField.validate() &&
                repeatUsernameField.validate() &&
                passwordField.validate() &&
                repeatPasswordField.validate();
    }

    @FXML
    void performRegister() {
        if (!canRegister()) {
            return;
        }
        AuthRequest authentication = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthRequest.class, Constants.HOST_URL);
        User user = new User(
                null,
                usernameField.getText(),
                passwordField.getText(),
                firstnameField.getText(),
                lastnameField.getText(),
                false,
                AuthorityEnum.DZIEKANAT
        );
        try {
            boolean registered = Boolean.parseBoolean(authentication.register(user));
            System.out.println("Registered");
        } catch (FeignException.Conflict conflict) {
            System.out.println("User already exist");
        }
    }

    @FXML
    private void goToLoginForm() {
        mainController.setLoginControls();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addRequiredValidator();
        addUsernameValidator();
        addUserDetailsValidator();
        addPasswordValidator();
        addFocusPropertyListeners();
    }

    private void addPasswordValidator() {
        PasswordValidator passwordValidator = new PasswordValidator(Constants.PASSWORD_PROMPT);
        passwordField.getValidators().add(passwordValidator);

        MatchingValidator matchingValidator = new MatchingValidator(
                "Passwords don't match",
                () -> passwordField.getText().equals(repeatPasswordField.getText())
        );
        repeatPasswordField.getValidators().add(matchingValidator);
    }

    private void addUserDetailsValidator() {
        UserDetailsValidator firstnameValidator = new UserDetailsValidator(
                "Firstname is too short or too long",
                1,
                30
        );
        firstnameField.getValidators().add(firstnameValidator);

        UserDetailsValidator lastnameValidator = new UserDetailsValidator(
                "Lastname is too short or too long",
                1,
                30
        );
        lastnameField.getValidators().add(lastnameValidator);
    }

    private void addUsernameValidator() {
        RegexValidator usernameValidator = new RegexValidator("Invalid email, example: example@gmail.com");
        usernameValidator.setRegexPattern(Constants.EMAIL_REGEX);
        usernameField.getValidators().add(usernameValidator);

        MatchingValidator matchingValidator = new MatchingValidator(
                "Usernames don't match",
                () -> usernameField.getText().equals(repeatUsernameField.getText())
        );
        repeatUsernameField.getValidators().add(matchingValidator);
    }

    private void addRequiredValidator() {
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator("Field required");
        firstnameField.getValidators().add(requiredValidator);
        lastnameField.getValidators().add(requiredValidator);
        usernameField.getValidators().add(requiredValidator);
        repeatUsernameField.getValidators().add(requiredValidator);
        passwordField.getValidators().add(requiredValidator);
        repeatPasswordField.getValidators().add(requiredValidator);
    }

    private void addFocusPropertyListeners() {
        addFocusPropertyListener(firstnameField);
        addFocusPropertyListener(lastnameField);
        addFocusPropertyListener(usernameField);
        addFocusPropertyListener(repeatUsernameField);
        addFocusPropertyListener(passwordField);
        addFocusPropertyListener(repeatPasswordField);
    }

    private void addFocusPropertyListener(JFXTextField node) {
        node.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                node.validate();
            }
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}