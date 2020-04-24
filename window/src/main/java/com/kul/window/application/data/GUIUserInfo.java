package com.kul.window.application.data;

import com.kul.api.model.AuthorityEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;

public class GUIUserInfo implements User {
    private final LongProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final ObservableBooleanValue enabled;
    private final StringProperty authority;

    public GUIUserInfo(Long id, String firstName, String lastName, String username, boolean enabled, AuthorityEnum authority) {
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.enabled = new SimpleBooleanProperty(enabled);
        this.authority = new SimpleStringProperty(authority.displayName);
    }

    @Override
    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    @Override
    public StringBinding username() {
        return Bindings.selectString(username);
    }

    @Override
    public StringBinding firstName() {
        return Bindings.selectString(firstName);
    }

    @Override
    public StringBinding lastName() {
        return Bindings.selectString(lastName);
    }

    @Override
    public StringBinding authority() {
        return Bindings.selectString(authority);
    }

    @Override
    public BooleanBinding canBeEnabled() {
        return Bindings.selectBoolean(enabled);
    }

    @Override
    public BooleanBinding canBeDisabled() {
        return Bindings.not(enabled);
    }

    @Override
    public ObservableBooleanValue enabled() {
        return enabled;
    }
}
