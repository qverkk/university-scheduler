package com.kul.database

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BaseIntegrationSpec extends Specification {

}
