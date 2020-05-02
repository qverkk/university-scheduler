package com.kul.database.lecturerpreferences.domain;

import com.kul.database.usermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface LecturerPreferencesRepository {
    Optional<LecturerPreferences> findByDayAndUser(DayOfWeek day, User user);
    LecturerPreferences save(LecturerPreferences lecturerPreferences);
}
