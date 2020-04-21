package com.kul.window.application.admin;

import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.api.domain.user.authorization.UserInfo;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private final UserInfo userInfo;
    private final ExistingUserToken existingUserToken;

    public AdminController(UserInfo userInfo, ExistingUserToken existingUserToken) {
        this.userInfo = userInfo;
        this.existingUserToken = existingUserToken;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
