package com.kul.window.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class LoginViewModel {
    private final BooleanProperty usernameError = new SimpleBooleanProperty(false);
    private final BooleanProperty passwordError = new SimpleBooleanProperty(false);
    private final BooleanProperty accountLocked = new SimpleBooleanProperty(false);

    public BooleanProperty usernameErrorProperty() {
        return usernameError;
    }

    public BooleanProperty passwordErrorProperty() {
        return passwordError;
    }

    public BooleanProperty accountLockedProperty() {
        return accountLocked;
    }
}
