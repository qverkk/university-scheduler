package com.kul.api.validators;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.util.concurrent.Callable;

public class MatchingValidator extends ValidatorBase {

    private final Callable<Boolean> callable;

    public MatchingValidator(String message, Callable<Boolean> callable) {
        super(message);
        this.callable = callable;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        hasErrors.set(false);
        try {
            if (!callable.call()) {
                hasErrors.set(true);
            }
        } catch (Exception e) {
            hasErrors.set(true);
        }
    }
}
