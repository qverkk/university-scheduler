package com.kul.database.lecturerpreferences.api.model;

import com.kul.database.infrastructure.constraints.AnyOfEnumValues;
import com.kul.database.lecturerpreferences.domain.UpdateLecturerPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateLecturerPreferenceRequest {
    @NotNull
    private Long userId;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String startTime;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String endTime;

    @NotNull
    @AnyOfEnumValues(DayOfWeek.class)
    private DayOfWeek day;

    public UpdateLecturerPreference toDomain() {
        return new UpdateLecturerPreference(
                userId,
                stringToLocalTime(startTime),
                stringToLocalTime(endTime),
                day
        );
    }

    private LocalTime stringToLocalTime(String str) {
        List<Integer> numbers = Arrays.stream(str.split(":"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return LocalTime.of(numbers.get(0), numbers.get(1));
    }
}
