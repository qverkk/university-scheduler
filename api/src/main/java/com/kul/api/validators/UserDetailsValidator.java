package com.kul.api.validators;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class UserDetailsValidator extends ValidatorBase {

    private int min;
    private int max;

    public UserDetailsValidator(String message, int min, int max) {
        super(message);
        this.min = min;
        this.max = max;
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

        if (!text.matches("\\w+")) {
            hasErrors.set(true);
            return;
        }
        if (text.length() < min || text.length() > max) {
            hasErrors.set(true);
            return;
        }
    }
}
