package com.kul.api.domain.admin.management;

import java.util.List;

public interface ManagementUserRepository {
    List<ManagedUser> getAllUsers();
    void enableUser(Long id);
    void disableUser(Long id);
}
