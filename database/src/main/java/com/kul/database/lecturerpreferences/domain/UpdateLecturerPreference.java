package com.kul.database.lecturerpreferences.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class UpdateLecturerPreference {
    @NotNull
    private final Long userId;

    @NotNull
    private final LocalTime startTime;

    @NotNull
    private final LocalTime endTime;

    @NotNull
    private final DayOfWeek day;
}
