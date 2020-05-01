package com.kul.database.lecturerpreferences.domain;

import com.kul.database.usermanagement.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(name = "lecturer_preferences")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LecturerPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private DayOfWeek day;

    @NotNull
    private User lastUsedUser;

    @Version
    private Long version;

    public LecturerPreferences(Long id, User user, LocalTime startTime, LocalTime endTime, DayOfWeek day) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.lastUsedUser = null;
    }

    public LecturerPreferences(User user, LocalTime startTime, LocalTime endTime, DayOfWeek day) {
        this(null, user, startTime, endTime, day);
    }

    public void changeScheduleWindow(LocalTime startTime, LocalTime endTime, User lastUsedUser) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastUsedUser = lastUsedUser;
    }

    public boolean canBeUpdatedBy(User user) {
        return user.canUpdatePreferencesOf(this.user);
    }
}
