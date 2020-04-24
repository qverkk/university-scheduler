package com.kul.window.application.data;

import javafx.collections.ObservableList;

public interface Users {
    ObservableList<UserInfoViewModel> users();
    void enableUser(Long id);
    void disableUser(Long id);
    void refresh();
}
