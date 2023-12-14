package com.ddcsoftware;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

// DON'T USE @SpringBootTest for Unit Tests unless configured properly
//it spins the whole app including Application Context and loads things we don't need
//this will just make the test slower

//All other Test classes will use this to init dependencies before testing
@Testcontainers
public abstract class AbstractTestcontainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()).load();

        //Load all migration versions from resources/db.migration/
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("ddc-dao-unit-test")
                    .withUsername("ddcsoftware")
                    .withPassword("1234");

    //@DynamicPropertySource() allows us to get @DynamicPropertySource()
    //@DynamicPropertyRegistryWrites to application.yml the new url,
    // so it connects to our temp testcontainers database
    //This replaces localhost during the tests
    @DynamicPropertySource()
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        //Add to this property field the test container db url
        registry.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl
        );
        //Add to this property field the test container db username
        registry.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername
        );
        //Add to this property field the test container db password
        registry.add(
                "spring.datasource.password",
                postgreSQLContainer::getPassword
        );
    }

    //This allows us to get the DB from the container
    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

    //Get Jdbc Template loaded with our container
    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
}
