package com.kul.window.application.helpers;

import com.kul.api.adapter.admin.areaofstudies.LessonsRepositoryFacade;
import com.kul.api.adapter.admin.classroomtypes.ClassroomTypesRepositoryFacade;
import com.kul.api.adapter.admin.external.*;
import com.kul.api.adapter.admin.management.ManagementUserRepositoryFacade;
import com.kul.api.domain.admin.areaofstudies.LessonsManagement;
import com.kul.api.domain.admin.classroomtypes.ClassroomTypesManagement;
import com.kul.api.domain.admin.management.UserManagement;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import com.kul.window.application.admin.AdminController;
import com.kul.window.application.data.AdminViewModel;
import com.kul.window.application.data.UpdatePreferenceViewModel;
import com.kul.window.application.data.UserInfoViewModel;
import com.kul.window.application.user.ApplicationController;
import com.kul.window.async.ExecutorsFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationWindowManager {

    private final ExecutorsFactory preconfiguredExecutors;
    private Stage currentStage;

    public ApplicationWindowManager(Stage currentStage, ExecutorsFactory preconfiguredExecutors) {
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
        ClassroomsEndpointClient classroomEndpointClient = new ClassroomTypesEndpointClientFactory(existingUserToken).create();
        LessonsEndpointClient areaOfStudiesEndpointClient = new AreaOfStudiesEndpointClientFactory(existingUserToken).create();

        UserManagement userManagement = new UserManagement(
                new ManagementUserRepositoryFacade(endpointClient)
        );

        ClassroomTypesManagement classroomTypesManagement = new ClassroomTypesManagement(
                new ClassroomTypesRepositoryFacade(classroomEndpointClient)
        );

        LessonsManagement areaOfStudiesManagement = new LessonsManagement(
                new LessonsRepositoryFacade(areaOfStudiesEndpointClient)
        );

        changeWindow(
                "/com/kul/window/panes/AdminWindow.fxml",
                "KUL Scheduler Admin panel",
                new AdminController(
                        new AdminViewModel(
                                preconfiguredExecutors,
                                userManagement,
                                classroomTypesManagement,
                                userInfo,
                                new UpdatePreferenceViewModel(),
                                areaOfStudiesManagement
                        )
                )
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
