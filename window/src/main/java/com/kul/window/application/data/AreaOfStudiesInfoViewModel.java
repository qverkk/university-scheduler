package com.kul.window.application.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AreaOfStudiesInfoViewModel {

    private final LongProperty id;
    private final StringProperty areaOfStudiesName;
    private final StringProperty departmentName;

    public AreaOfStudiesInfoViewModel(Long id, String areaOfStudiesName, String departmentName) {
        this.id = new SimpleLongProperty(id);
        this.areaOfStudiesName = new SimpleStringProperty(areaOfStudiesName);
        this.departmentName = new SimpleStringProperty(departmentName);
    }

    public LongBinding id() {
        return Bindings.selectLong(id);
    }

    public StringBinding areaOfStudiesName() {
        return Bindings.selectString(areaOfStudiesName);
    }

    public StringBinding departmentName() {
        return Bindings.selectString(departmentName);
    }

    @Override
    public String toString() {
        return departmentName.get() + " " + areaOfStudiesName.get();
    }
}
