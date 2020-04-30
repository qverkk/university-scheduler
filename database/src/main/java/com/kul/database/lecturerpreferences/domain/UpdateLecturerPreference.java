package com.kul.database.lecturerpreferences.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreference {
    @NotNull
    private Long userId;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String startTime;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String endTime;

    @NotNull
    private DayOfWeek day;
}
