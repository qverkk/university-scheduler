package com.kul.window;

import com.kul.api.adapter.user.authorization.UserAuthorizationFacade;
import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.adapter.user.external.AuthEndpointClientFactory;
import com.kul.api.adapter.user.registration.UserRepositoryFacade;
import com.kul.api.domain.user.registration.UserRegistration;
import com.kul.window.application.helpers.ApplicationWindowManager;
import com.kul.window.application.helpers.ApplicationWindowManagerFactory;
import com.kul.window.async.ExecutorsFactory;
import com.kul.window.async.PreconfiguredExecutors;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private final ExecutorsFactory preconfiguredExecutors = new PreconfiguredExecutors(
            JavaFxScheduler.platform()
    );

    private final AuthEndpointClient authEndpointClient = new AuthEndpointClientFactory().create();

    private final UserRegistration userRegistration = new UserRegistration(
            new UserRepositoryFacade(authEndpointClient)
    );

    private final UserAuthorizationFacade userAuthorizationFacade = new UserAuthorizationFacade(authEndpointClient);

    private final ApplicationWindowManagerFactory applicationWindowManagerFactory = new ApplicationWindowManagerFactory(
            preconfiguredExecutors
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kul/window/panes/MainWindow.fxml"));

        final ApplicationWindowManager applicationWindowManager = applicationWindowManagerFactory.createForStage(primaryStage);

        loader.setController(new MainController(
                applicationWindowManager,
                userAuthorizationFacade,
                preconfiguredExecutors,
                userRegistration
        ));

        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/com/kul/window/images/KUL_icon.jpg"));
        primaryStage.setTitle("Login panel");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
