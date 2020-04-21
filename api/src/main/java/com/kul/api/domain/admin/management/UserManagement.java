package com.kul.api.domain.admin.management;

import lombok.AllArgsConstructor;

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
}
