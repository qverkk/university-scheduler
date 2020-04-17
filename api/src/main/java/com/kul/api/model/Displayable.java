package com.kul.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Displayable<T> {
    private final T value;
    private final String content;

    @Override
    public String toString() {
        return content;
    }
}
