package com.kul.api.domain.admin.management;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserManagement {
    private final ManagementUserRepository usersRepository;

    public List<ManagedUser> getAllUsers() {
        return usersRepository.getAllUsers();
    }
}
