package com.kul.window.application.admin.data;

import com.kul.api.model.AuthorityEnum;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperty {
    private final StringProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final BooleanProperty enabled;
    private final StringProperty authority;

    public UserProperty(Long id, String firstName, String lastName, String username, boolean enabled, AuthorityEnum authority) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.enabled = new SimpleBooleanProperty(enabled);
        this.authority = new SimpleStringProperty(authority.displayName);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    public String getAuthority() {
        return authority.get();
    }

    public StringProperty authorityProperty() {
        return authority;
    }
}
