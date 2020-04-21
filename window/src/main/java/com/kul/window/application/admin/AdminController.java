package com.kul.window.application.admin;

import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.UserInfo;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private final UserInfo userInfo;
    private final UserManagement userManagement;

    public AdminController(UserInfo userInfo, UserManagement userManagement) {
        this.userInfo = userInfo;
        this.userManagement = userManagement;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
