package com.kul.api.domain.user.registration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRegistration {
    private final UserRepository usersRepository;

    public RegistrationInfo register(NewUser newUser) {
        User notRegistered = User.from(newUser);
        RegisteredInfo registered = usersRepository.save(notRegistered);
        return registered.getInfo();
    }
}
