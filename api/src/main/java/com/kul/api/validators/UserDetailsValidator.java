package com.kul.api.validators;

import com.jfoenix.validation.base.ValidatorBase;
import com.kul.api.data.Constants;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

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

        if (text.length() < min || text.length() > max) {
            hasErrors.set(true);
            return;
        }

        if (!Constants.SIMPLE_STRING_PATTERN.matcher(text).matches()) {
            hasErrors.set(true);
        }
    }
}
