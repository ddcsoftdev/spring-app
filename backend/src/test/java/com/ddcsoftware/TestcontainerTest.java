package com.ddcsoftware;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TestcontainerTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }
}
