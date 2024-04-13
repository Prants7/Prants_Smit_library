package com.prants

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class Prants_Library_gradleTest {

    @Inject
    EmbeddedApplication<?> application

    @Test
    void testItWorks() {
        assert application.running == true
    }

}
