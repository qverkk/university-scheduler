package com.kul.window.application.helpers;

import com.kul.window.async.ExecutorsFactory;
import javafx.stage.Stage;

public class ApplicationWindowManagerFactory {
    private final ExecutorsFactory executorsFactory;

    public ApplicationWindowManagerFactory(ExecutorsFactory executorsFactory) {
        this.executorsFactory = executorsFactory;
    }

    public ApplicationWindowManager createForStage(Stage stage) {
        return new ApplicationWindowManager(stage, executorsFactory);
    }
}
