package com.kul.api.domain.admin.management;

import java.time.DayOfWeek;
import java.util.List;

public interface ManagementUserRepository {
    List<ManagedUser> getAllUsers();
    void enableUser(Long id);
    void disableUser(Long id);
    LecturerPreferences updatePreferences(LecturerPreferences preferences) throws Exception;
    LecturerPreferences addPreferences(LecturerPreferences preferences) throws Exception;
    LecturerPreferences fetchPreferences(Long userId, DayOfWeek day) throws Exception;
}
