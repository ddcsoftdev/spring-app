package com.ddcsoftware;

import old.CustomerJPA;
import com.ddcsoftware.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
            CustomerJPA diego = new CustomerJPA("Diego", "diego@gmail.com", 25);
            CustomerJPA alisha = new CustomerJPA("Alisha", "alisha@gmail.com", 29);
            List<CustomerJPA> list = List.of(diego, alisha);
            customerRepository.saveAll(list);
        };
    }
}
