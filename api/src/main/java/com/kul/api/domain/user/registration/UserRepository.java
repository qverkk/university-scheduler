package com.kul.api.domain.user.registration;

import com.kul.api.adapter.user.registration.UserAccountAlreadyExistException;

public interface UserRepository {
    RegisteredInfo save(User user) throws UserAccountAlreadyExistException;
}
