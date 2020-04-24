package com.kul.window.application.data;

import com.kul.api.domain.admin.management.ManagedUser;
import com.kul.window.application.admin.AdminController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIUsers implements Users {

    private final List<UserInfoViewModel> users = new LinkedList<>();
    private final AdminController adminController;
    private final ObservableList<UserInfoViewModel> observableUsers = FXCollections.observableList(users);
    private volatile boolean fetchingLocked = false;

    public GUIUsers(AdminController adminController) {
        this.adminController = adminController;
    }

    @Override
    public ObservableList<UserInfoViewModel> users() {
        return observableUsers;
    }

    @Override
    public void enableUser(Long id) {
        if (fetchingLocked) {
            return;
        }
        new Thread(() -> {
            adminController.getUserManagement().enableUser(id);
            refresh();
        }).start();
    }

    @Override
    public void disableUser(Long id) {
        if (fetchingLocked) {
            return;
        }
        new Thread(() -> {
            adminController.getUserManagement().disableUser(id);
            refresh();
        }).start();
    }

    @Override
    public void refresh() {
        if (fetchingLocked) {
            return;
        }
        fetchingLocked = true;
        users.clear();
        new Thread(() -> {
            final List<ManagedUser> requestedUsers = adminController.getUserManagement().getAllUsers();
            List<UserInfoViewModel> userPropertyList = requestedUsers.stream().map(u ->
                    new UserInfoViewModel(
                            u.getId(),
                            u.getFirstName(),
                            u.getLastName(),
                            u.getUsername(),
                            u.getEnabled(),
                            u.getAuthority()
                    )
            ).collect(Collectors.toList());
            Platform.runLater(() -> observableUsers.addAll(userPropertyList));
            fetchingLocked = false;
        }).start();
    }
}
