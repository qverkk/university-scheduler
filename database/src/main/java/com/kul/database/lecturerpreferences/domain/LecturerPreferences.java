package com.kul.database.lecturerpreferences.domain;

import com.kul.database.usermanagement.domain.User;
import lombok.*;

import javax.persistence.*;
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

    private String startTime;

    private String endTime;

    private DayOfWeek day;
}
