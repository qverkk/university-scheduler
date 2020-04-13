package com.kul.api.validators;

import com.jfoenix.validation.base.ValidatorBase;
import com.kul.api.data.Constants;
import javafx.scene.control.TextInputControl;

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

        if (!text.matches(Constants.PASSWORD_REGEX)) {
            hasErrors.set(true);
        }
    }
}
