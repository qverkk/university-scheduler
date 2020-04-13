package com.kul.api.validators;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import com.kul.api.data.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;

public class PasswordValidator extends ValidatorBase {

    public PasswordValidator(String message) {
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
        if (!text.matches(Constants.PASSWORD_REGEX)) {
            hasErrors.set(true);
            VBox.setMargin(control, new Insets(0, 0, 100, 0));
        } else {
            VBox.setMargin(control, new Insets(0, 0, 0, 0));
        }
    }
}
