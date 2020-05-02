package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.usermanagement.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(name = "lecturer_preferences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class LecturerPreferencesEntity {
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
    private String lastUsedUsername;

    @Version
    private Long version;

    public static LecturerPreferencesEntity fromDomain(LecturerPreferences lecturerPreferences) {
        return new LecturerPreferencesEntity(
                lecturerPreferences.getId(),
                lecturerPreferences.getUser(),
                lecturerPreferences.getStartTime(),
                lecturerPreferences.getEndTime(),
                lecturerPreferences.getDay(),
                lecturerPreferences.getLastUsedUsername(),
                lecturerPreferences.getVersion()
        );
    }

    public static LecturerPreferences toDomain(LecturerPreferencesEntity entity) {
        return new LecturerPreferences(
                entity.id,
                entity.user,
                entity.startTime,
                entity.endTime,
                entity.day,
                entity.lastUsedUsername,
                entity.version
        );
    }
}
