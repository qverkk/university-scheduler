package com.kul.api.adapter.admin.external;

import com.kul.api.data.Constants;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class ClassroomTypesEndpointClientFactory {

    private final ExistingUserToken existingUserToken;

    public ClassroomTypesEndpointClientFactory(ExistingUserToken existingUserToken) {
        this.existingUserToken = existingUserToken;
    }

    public ClassroomsEndpointClient create() {
        return Feign.builder()
                .requestInterceptor(new AuthorizationInterceptor(existingUserToken))
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .errorDecoder(new JsonErrorAwareErrorDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.FULL)
                .target(ClassroomsEndpointClient.class, Constants.HOST_URL);
    }
}
