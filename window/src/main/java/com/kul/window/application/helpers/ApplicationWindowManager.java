package com.kul.window.application.helpers;

import com.kul.api.adapter.admin.external.ManagementEndpointClient;
import com.kul.api.adapter.admin.external.ManagementEndpointClientFactory;
import com.kul.api.adapter.admin.management.ManagementUserRepositoryFacade;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.window.application.admin.AdminController;
import com.kul.window.application.data.UserInfoViewModel;
import com.kul.window.application.user.ApplicationController;
import com.kul.window.async.PreconfiguredExecutors;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationWindowManager {

    private Stage currentStage;
    private final PreconfiguredExecutors preconfiguredExecutors;

    public ApplicationWindowManager(Stage currentStage, PreconfiguredExecutors preconfiguredExecutors) {
        this.currentStage = currentStage;
        this.preconfiguredExecutors = preconfiguredExecutors;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    private void closeCurrentStage() {
        currentStage.close();
    }

    public void openApplication(UserInfoViewModel userInfo) throws IOException {
        changeWindow(
                "/com/kul/window/panes/MainApplication.fxml",
                "KUL Scheduler",
                new ApplicationController(userInfo)
        );
    }

    public void openAdminPanel(UserInfoViewModel userInfo, ExistingUserToken existingUserToken) throws IOException {
        ManagementEndpointClient endpointClient = new ManagementEndpointClientFactory(existingUserToken).create();

        UserManagement userManagement = new UserManagement(
                new ManagementUserRepositoryFacade(endpointClient)
        );

        changeWindow(
                "/com/kul/window/panes/AdminWindow.fxml",
                "KUL Scheduler Admin panel",
                new AdminController(userInfo, userManagement, preconfiguredExecutors)
        );
    }

    private void changeWindow(String resource, String windowTitle, Initializable controller) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        loader.setController(controller);

        Parent root = loader.load();
        stage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));
        stage.show();

        closeCurrentStage();
    }
}
