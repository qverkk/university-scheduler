package com.kul.api.domain.user.registration;

import com.kul.api.adapter.user.registration.UserAccountAlreadyExistException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRegistration {
    private final UserRepository usersRepository;

    public RegistrationInfo register(NewUser newUser) throws UserAccountAlreadyExistException {
        User notRegistered = User.from(newUser);
        RegisteredInfo registered = usersRepository.save(notRegistered);
        return registered.getInfo();
    }
}
