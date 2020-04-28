package com.kul.database.repository;

import com.kul.database.model.LecturerPreferences;
import com.kul.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface LecturerPreferencesRepository extends JpaRepository<LecturerPreferences, Long> {
    Boolean findByDayAndUser(DayOfWeek day, User user);
    List<LecturerPreferences> findAllByUser(User user);
}
