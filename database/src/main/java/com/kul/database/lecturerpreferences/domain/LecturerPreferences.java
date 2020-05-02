package com.kul.database.lecturerpreferences.domain;

import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceInvalidTime;
import com.kul.database.usermanagement.domain.User;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class LecturerPreferences {
    private final Long id;
    private final User user;
    private final DayOfWeek day;
    private final Long version;

    private LocalTime startTime;
    private LocalTime endTime;
    private String lastUsedUsername;

    public LecturerPreferences(Long id, User user, LocalTime startTime, LocalTime endTime, DayOfWeek day, String lastUsedUsername, Long version) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.lastUsedUsername = lastUsedUsername;
        this.version = version;
    }

    public static LecturerPreferences newForDay(User owner, DayOfWeek day) {
        return new LecturerPreferences(null, owner, null, null, day, null, 1L);
    }

    public void changeScheduleWindow(LocalTime startTime, LocalTime endTime, User lastUsedUser) {
        if (startTime.isAfter(endTime)) {
            throw new LecturerPreferenceInvalidTime("Start time must be before end time");
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.lastUsedUsername = lastUsedUser.getUsername();
    }

    public boolean canBeUpdatedBy(User user) {
        return user.canUpdatePreferencesOf(this.user);
    }
}