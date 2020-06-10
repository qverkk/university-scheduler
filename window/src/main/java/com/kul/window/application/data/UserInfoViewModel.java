package com.kul.window.application.data;

import com.kul.api.model.AuthorityEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;

public class UserInfoViewModel {
    private final LongProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final ObservableBooleanValue enabled;
    private final StringProperty authority;

    public UserInfoViewModel(Long id, String firstName, String lastName, String username, boolean enabled, AuthorityEnum authority) {
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.enabled = new SimpleBooleanProperty(enabled);
        this.authority = new SimpleStringProperty(authority.displayName);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    public StringBinding username() {
        return Bindings.selectString(username);
    }

    public StringBinding firstName() {
        return Bindings.selectString(firstName);
    }

    public StringBinding lastName() {
        return Bindings.selectString(lastName);
    }

    public StringBinding authority() {
        return Bindings.selectString(authority);
    }

    public BooleanBinding canBeEnabled() {
        return Bindings.selectBoolean(enabled);
    }

    public BooleanBinding canBeDisabled() {
        return Bindings.not(enabled);
    }

    public ObservableBooleanValue enabled() {
        return enabled;
    }

    public boolean canContainPreferences() {
        return authority.get().equals(AuthorityEnum.PROWADZACY.displayName);
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }
}
