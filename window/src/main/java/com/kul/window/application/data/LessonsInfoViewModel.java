package com.kul.window.application.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LessonsInfoViewModel {
    private final LongProperty id;
    private final LongProperty userId;
    private final StringProperty lecturersName;
    private final StringProperty lessonName;
    private final StringProperty areaOfStudy;
    private final StringProperty semester;
    private final StringProperty year;
    private final StringProperty lessonType;

    public LessonsInfoViewModel(Long id, Long userId, String lecturersName, String lessonName, String areaOfStudy, String semester, String year, String lessonType) {
        this.id = new SimpleLongProperty(id);
        this.userId = new SimpleLongProperty(userId);
        this.lecturersName = new SimpleStringProperty(lecturersName);
        this.lessonName = new SimpleStringProperty(lessonName);
        this.areaOfStudy = new SimpleStringProperty(areaOfStudy);
        this.semester = new SimpleStringProperty(semester);
        this.year = new SimpleStringProperty(year);
        this.lessonType = new SimpleStringProperty(lessonType);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    public LongBinding userId() {
        return Bindings.selectLong(userId);
    }

    public StringBinding lecturersName() {
        return Bindings.selectString(lecturersName);
    }

    public StringBinding lessonName() {
        return Bindings.selectString(lessonName);
    }

    public StringBinding areaOfStudy() {
        return Bindings.selectString(areaOfStudy);
    }

    public StringBinding semester() {
        return Bindings.selectString(semester);
    }

    public StringBinding year() {
        return Bindings.selectString(year);
    }

    public StringBinding lessonType() {
        return Bindings.selectString(lessonType);
    }
}
