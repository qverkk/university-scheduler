package com.kul.window.application.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassroomTypesInfoViewModel {
    private final LongProperty id;
    private final StringProperty classroomTypeName;

    public ClassroomTypesInfoViewModel(Long id, String classroomTypeName) {
        this.id = new SimpleLongProperty(id);
        this.classroomTypeName = new SimpleStringProperty(classroomTypeName);
    }

    public StringBinding typeName() {
        return Bindings.selectString(classroomTypeName);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }
}
