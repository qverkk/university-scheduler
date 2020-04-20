package com.kul.window;

import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.adapter.user.external.AuthEndpointClientFactory;
import com.kul.api.adapter.user.registration.UserRepositoryFacade;
import com.kul.api.domain.user.registration.UserRegistration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private final AuthEndpointClient authEndpointClient = new AuthEndpointClientFactory().create();

    private final UserRegistration userRegistration = new UserRegistration(
            new UserRepositoryFacade(authEndpointClient)
    );

    private final UserAuthorizationFacade userAuthorizationFacade = new UserAuthorizationFacade(authEndpointClient);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kul/window/panes/MainWindow.fxml"));
        loader.setController(new MainController(
                userRegistration,
                userAuthorizationFacade
        ));
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        primaryStage.setTitle("Login panel");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
