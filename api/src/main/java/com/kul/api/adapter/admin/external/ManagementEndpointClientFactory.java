package com.kul.api.adapter.admin.external;

import com.kul.api.data.Constants;
import com.kul.api.domain.user.authorization.ExistingUserToken;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class ManagementEndpointClientFactory {

    private final ExistingUserToken existingUserToken;

    public ManagementEndpointClientFactory(ExistingUserToken existingUserToken) {
        this.existingUserToken = existingUserToken;
    }

    public ManagementEndpointClient create() {
        return Feign.builder()
                .requestInterceptor(new AuthorizationInterceptor(existingUserToken))
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .errorDecoder(new JsonErrorAwareErrorDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.FULL)
                .target(ManagementEndpointClient.class, Constants.HOST_URL);
    }
}
