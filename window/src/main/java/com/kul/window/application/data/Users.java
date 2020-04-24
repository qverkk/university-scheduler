package com.kul.window.application.data;

import javafx.collections.ObservableList;

public interface Users {
    ObservableList<User> users();
    void enableUser(Long id);
    void disableUser(Long id);
    void refresh();
}
