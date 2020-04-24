package com.kul.api.adapter.user.external;

import com.kul.api.data.Constants;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class AuthEndpointClientFactory {
    public AuthEndpointClient create() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.FULL)
                .target(AuthEndpointClient.class, Constants.HOST_URL);
    }
}
