package com.kul.database.usermanagement.domain;

public interface UserManagementPermissions {
    boolean canDisableUsers(String username);
    boolean canEnableUsers(String username);
    boolean canDeleteUsers(String username);
    boolean canHaveAccessToAllUserData(String username);
}
