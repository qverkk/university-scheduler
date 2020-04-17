package com.kul.database.abilities

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.server.LocalServerPort

trait RequestLocalServerAbility {
    private final ObjectMapper objectMapper = new ObjectMapper()

    @LocalServerPort
    int localServerPort

    URI baseUrl() {
        URI.create("http://localhost:$localServerPort")
    }

    ObjectMapper objectMapper() {
        objectMapper
    }
}
