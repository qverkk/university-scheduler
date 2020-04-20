package com.kul.api.adapter.user.registration;

import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.domain.user.registration.User;
import com.kul.api.domain.user.registration.UserRepository;
import feign.FeignException;

public class UserRepositoryFacade implements UserRepository {

    private final AuthEndpointClient authentication;

    public UserRepositoryFacade(AuthEndpointClient authentication) {
        this.authentication = authentication;
    }

    @Override
    public User save(User user) throws UserAccountAlreadyExistException, UserAccountCreationException {
        final UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEnabled(),
                user.getAuthority()
        );

        try {
            final UserRegistrationResponse userRegistrationResponse = authentication.register(userRegistrationRequest);
            user.setId(userRegistrationResponse.getNewUserAssignedId());

            System.out.println(userRegistrationResponse.getNewUserAssignedId());
            System.out.println(userRegistrationResponse.getSuccess());
            if (!userRegistrationResponse.getSuccess()) {
                throw new UserAccountCreationException();
            }
            return user;
//            return RegisteredInfo.from(user, new RegistrationInfo(user.getId(), userRegistrationResponse.getSuccess()));
        } catch (FeignException.Conflict conflict) {
            throw new UserAccountAlreadyExistException();
        }
    }
}
