package com.kul.api.domain.user.registration;

import com.kul.api.adapter.user.registration.UserAccountAlreadyExistException;
import com.kul.api.adapter.user.registration.UserAccountCreationException;

public interface UserRepository {
    User save(User user) throws UserAccountAlreadyExistException, UserAccountCreationException;
}
