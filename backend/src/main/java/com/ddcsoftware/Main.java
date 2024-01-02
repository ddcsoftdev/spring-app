package com.ddcsoftware;

import com.ddcsoftware.customer.Customer;
import com.ddcsoftware.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class Main {
    private Customer diego;

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
                    faker.random().nextInt(18, 99));
            customerRepository.save(customerOne);
        };
    }
}