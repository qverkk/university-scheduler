package com.kul.api.validators;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.geometry.Insets;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import org.passay.*;

import java.util.Arrays;

public class PasswordValidation extends ValidatorBase {

    private static final PasswordValidator PASSWORD_VALIDATOR = new PasswordValidator(Arrays.asList(
            new LengthRule(12, 64),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1)
    ));

    public PasswordValidation(String message) {
        super(message);
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl input = (TextInputControl) srcControl.get();
        String text = input.getText();
        hasErrors.set(false);

        JFXPasswordField control = (JFXPasswordField) srcControl.get();

        RuleResult result = PASSWORD_VALIDATOR.validate(new PasswordData(text));

//        if (!text.matches(Constants.PASSWORD_REGEX)) {
        if (!result.isValid()) {
            hasErrors.set(true);
            VBox.setMargin(control, new Insets(0, 0, 100, 0));
        } else {
            VBox.setMargin(control, new Insets(0, 0, 0, 0));
        }
    }
}
