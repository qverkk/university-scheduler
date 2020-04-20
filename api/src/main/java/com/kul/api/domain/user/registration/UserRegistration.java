package com.kul.api.domain.user.registration;

import com.kul.api.adapter.user.registration.UserAccountAlreadyExistException;
import com.kul.api.adapter.user.registration.UserAccountCreationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRegistration {
    private final UserRepository usersRepository;

    public RegistrationInfo register(NewUser newUser) throws UserAccountAlreadyExistException, UserAccountCreationException {
        User notRegistered = User.from(newUser);
        User registered = usersRepository.save(notRegistered);
        return new RegistrationInfo(registered.getId(), true);
    }
}
