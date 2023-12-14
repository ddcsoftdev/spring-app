package com.ddcsoftware;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestcontainerTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("ddc-dao-unit-test")
                    .withUsername("ddcsoftware")
                    .withPassword("1234");

    @Test
    void canStartPostgresDB() {
          assertThat(postgreSQLContainer.isCreated()).isTrue();
          assertThat(postgreSQLContainer.isRunning()).isTrue();
    }
}
