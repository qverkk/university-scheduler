package com.kul.api.domain.user.registration;

public interface UserRepository {
    RegisteredInfo save(User user);
}
