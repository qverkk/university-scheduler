package com.kul.database.lecturerpreferences.adapter;

import com.kul.database.lecturerpreferences.domain.LecturerPreferences;
import com.kul.database.lecturerpreferences.domain.LecturerPreferencesRepository;
import com.kul.database.usermanagement.domain.User;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
class JpaLecturerPreferencesRepositoryFacade implements LecturerPreferencesRepository {
    private final JpaLecturerPreferencesRepository lecturerPreferencesRepository;

    public JpaLecturerPreferencesRepositoryFacade(JpaLecturerPreferencesRepository lecturerPreferencesRepository) {
        this.lecturerPreferencesRepository = lecturerPreferencesRepository;
    }

    @Override
    public Optional<LecturerPreferences> findByDayAndUser(DayOfWeek day, User user) {
        return lecturerPreferencesRepository.findByDayAndUser(day, user)
                .map(LecturerPreferencesEntityMapper::toDomain);
    }

    @Override
    public LecturerPreferences save(LecturerPreferences lecturerPreferences) {
        LecturerPreferencesEntity entity = LecturerPreferencesEntity.fromDomain(lecturerPreferences);

        LecturerPreferencesEntity saved = lecturerPreferencesRepository.save(entity);

        return LecturerPreferencesEntity.toDomain(saved);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        lecturerPreferencesRepository.deleteAllByUserId(userId);
    }
}