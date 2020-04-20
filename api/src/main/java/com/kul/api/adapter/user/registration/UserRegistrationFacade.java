package com.kul.api.adapter.user.registration;

import com.kul.api.adapter.user.external.AuthEndpointClient;
import com.kul.api.data.Constants;
import com.kul.api.domain.user.registration.RegisteredInfo;
import com.kul.api.domain.user.registration.RegistrationInfo;
import com.kul.api.domain.user.registration.User;
import com.kul.api.domain.user.registration.UserRepository;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class UserRegistrationFacade implements UserRepository {

    private final AuthEndpointClient authentication = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(AuthEndpointClient.class, Constants.HOST_URL);

    @Override
    public RegisteredInfo save(User user) throws UserAccountAlreadyExistException {
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

            return RegisteredInfo.from(user, new RegistrationInfo(user.getId(), userRegistrationResponse.getSuccess()));
        } catch (FeignException.Conflict conflict) {
            throw new UserAccountAlreadyExistException();
        }
    }
}
