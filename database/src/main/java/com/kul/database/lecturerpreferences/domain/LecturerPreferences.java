package com.kul.database.lecturerpreferences.domain;

import com.kul.database.usermanagement.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;

@Entity(name = "lecturer_preferences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LecturerPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String startTime;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String endTime;

    @NotNull
    private DayOfWeek day;
}
