package com.kul.window.registration;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegistrationViewModel {
    private final StringProperty registrationStatus = new SimpleStringProperty();
    private final BooleanProperty usernameError = new SimpleBooleanProperty(false);

    public StringProperty registrationStatusProperty() {
        return registrationStatus;
    }

    public BooleanProperty usernameErrorProperty() {
        return usernameError;
    }
}
