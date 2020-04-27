package com.kul.window.registration;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.kul.api.data.Constants;
import com.kul.api.model.AuthorityEnum;
import com.kul.api.model.Displayable;
import com.kul.api.validators.MatchingValidator;
import com.kul.api.validators.PasswordValidation;
import com.kul.api.validators.UserDetailsValidator;
import com.kul.window.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Y510p
 * @project university-scheduler
 * @date 30.03.2020
 **/

public class RegistrationController implements Initializable {

    private final RegistrationViewModel registrationViewModel;

    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField repeatUsernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField repeatPasswordField;
    @FXML
    private JFXComboBox<Displayable<AuthorityEnum>> authorityCb;
    @FXML
    private Label usernameError;
    @FXML
    private Label registrationSuccess;

    public RegistrationController(RegistrationViewModel registrationViewModel) {
        this.registrationViewModel = registrationViewModel;
    }

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
        registrationViewModel.register(
                usernameField.getText(),
                passwordField.getText(),
                firstnameField.getText(),
                lastnameField.getText(),
                authorityCb.getSelectionModel().getSelectedItem().getValue()
        );
    }

    @FXML
    private void goToLoginForm() {
        registrationViewModel.openLoginMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAuthoritiesToComboBox();
        addRequiredValidator();
        addUsernameValidator();
        addUserDetailsValidator();
        addPasswordValidator();
        addFocusPropertyListeners();
        initializeBindings();
    }

    private void initializeBindings() {
        usernameError.visibleProperty()
                .bind(registrationViewModel.usernameErrorProperty());
        registrationSuccess.textProperty()
                .bind(registrationViewModel.registrationStatusProperty());
        registrationSuccess.visibleProperty()
                .bind(registrationViewModel.registrationStatusProperty().isNotEmpty());
    }

    private void addAuthoritiesToComboBox() {
        authorityCb.getItems().addAll(
                getDisplayableFor(AuthorityEnum.DZIEKANAT),
                getDisplayableFor(AuthorityEnum.PROWADZACY)
        );
        authorityCb.getSelectionModel().selectFirst();
    }

    private Displayable<AuthorityEnum> getDisplayableFor(AuthorityEnum authority) {
        return new Displayable<>(authority, authority.displayName);
    }

    private void addPasswordValidator() {
        PasswordValidation passwordValidation = new PasswordValidation(Constants.PASSWORD_PROMPT);
        passwordField.getValidators().add(passwordValidation);

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
        MatchingValidator usernameValidator = new MatchingValidator(
                "Invalid email, example: example@gmail.com",
                () -> Constants.EMAIL_VALIDATOR.isValid(usernameField.getText())
        );

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

    private void addFocusPropertyListener(TextField node) {
        node.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                if (node instanceof JFXTextField) {
                    ((JFXTextField) node).validate();
                } else if (node instanceof JFXPasswordField) {
                    ((JFXPasswordField) node).validate();
                }
            }
        });
    }
}
