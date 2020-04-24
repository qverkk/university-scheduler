package com.kul.window.application.data;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableBooleanValue;

public interface User {
    LongBinding id();
    StringBinding username();
    StringBinding firstName();
    StringBinding lastName();
    StringBinding authority();
    BooleanBinding canBeEnabled();
    BooleanBinding canBeDisabled();
    ObservableBooleanValue enabled();
}
