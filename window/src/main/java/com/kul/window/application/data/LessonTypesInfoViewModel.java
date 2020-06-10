package com.kul.window.application.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LessonTypesInfoViewModel {
    private final LongProperty id;
    private final StringProperty lessonTypeName;

    public LessonTypesInfoViewModel(Long id, String lessonTypeName) {
        this.id = new SimpleLongProperty(id);
        this.lessonTypeName = new SimpleStringProperty(lessonTypeName);
    }

    public StringBinding typeName() {
        return Bindings.selectString(lessonTypeName);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    @Override
    public String toString() {
        return lessonTypeName.get();
    }
}
