package com.kul.window.application.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UpdatePreferenceViewModel {
    private final StringProperty currentlyEditedPreferenceStartTime = new SimpleStringProperty("00:00");
    private final StringProperty currentlyEditedPreferenceEndTime = new SimpleStringProperty("00:00");

    public StringProperty currentlyEditedPreferenceEndTimeProperty() {
        return currentlyEditedPreferenceEndTime;
    }

    public StringProperty currentlyEditedPreferenceStartTimeProperty() {
        return currentlyEditedPreferenceStartTime;
    }
}
