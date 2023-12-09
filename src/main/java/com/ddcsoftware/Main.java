package com.ddcsoftware;

import com.ddcsoftware.customer.Customer;
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
            Customer diego = new Customer("Diego", "diego@gmail.com", 25);
            Customer alisha = new Customer("Alisha", "alisha@gmail.com", 29);
            List<Customer> list = List.of(diego, alisha);
            customerRepository.saveAll(list);
        };
    }
}
