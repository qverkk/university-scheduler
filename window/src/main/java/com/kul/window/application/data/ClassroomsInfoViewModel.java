package com.kul.window.application.data;

import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClassroomsInfoViewModel {


    private final LongProperty id;
    private final StringProperty classroomName;
    private final ListProperty<String> classroomTypes;
    private final IntegerProperty classroomSize;

    public ClassroomsInfoViewModel(Long id, String name, List<String> classroomTypes, Integer classroomSize) {
        this.id = new SimpleLongProperty(id);
        this.classroomName = new SimpleStringProperty(name);
        this.classroomTypes = new SimpleListProperty<>(FXCollections.observableList(classroomTypes));
        this.classroomSize = new SimpleIntegerProperty(classroomSize);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    public StringBinding classroomName() {
        return Bindings.selectString(classroomName);
    }

    public StringBinding classroomTypes() {
        final StringBuilder sb = new StringBuilder();
        AtomicBoolean added = new AtomicBoolean(false);
        classroomTypes.forEach(e -> {
            if (added.get()) {
                sb.append(", ").append(e);
            } else {
                sb.append(e);
            }
            added.set(true);
        });
        return Bindings.selectString(new SimpleStringProperty(sb.toString()));
    }

    public StringBinding classroomSize() {
        return Bindings.selectString(classroomSize);
    }

    public List<String> types() {
        return new LinkedList<>(classroomTypes);
    }
}
