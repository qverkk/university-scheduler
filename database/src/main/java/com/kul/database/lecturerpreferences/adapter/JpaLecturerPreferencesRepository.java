package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.usermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

interface JpaLecturerPreferencesRepository extends JpaRepository<LecturerPreferencesEntity, Long> {
    Optional<LecturerPreferencesEntity> findByDayAndUser(DayOfWeek day, User user);
    List<LecturerPreferencesEntity> findAllByUser(User user);
}