package com.kul.api.domain.admin.management;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@AllArgsConstructor
public class UserManagement {
    private final ManagementUserRepository usersRepository;

    public List<ManagedUser> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    public void enableUser(Long id) {
        usersRepository.enableUser(id);
    }

    public void disableUser(Long id) {
        usersRepository.disableUser(id);
    }

    public LecturerPreferences updatePreferences(LecturerPreferences preferences) throws RuntimeException {
        return usersRepository.updatePreferences(preferences);
    }

    public LecturerPreferences fetchPreferences(Long userId, DayOfWeek selectedItem) throws RuntimeException {
        return usersRepository.fetchPreferences(userId, selectedItem);
    }
}
