package com.ddcsoftware;

import com.ddcsoftware.customer.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext
                = SpringApplication.run(Main.class, args);
    }

    //This is used for testing
    //Populates database on application start
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Faker faker = new Faker();
            Customer customerOne = new Customer(
                    faker.name().firstName(),
                    faker.name().lastName() + "@example.com",
                    "password", faker.random().nextInt(18, 99),
                    faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE);
            customerRepository.save(customerOne);
        };
    }
}