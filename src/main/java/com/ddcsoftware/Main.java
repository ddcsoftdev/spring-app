package com.ddcsoftware;

import com.ddcsoftware.models.Client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //Testing bed
    @GetMapping("api/v1/customers")
    public List<Client> getClients() {
        List<Client> customers = new ArrayList<Client>();

        customers.add(
                new Client()
        );
        return customers;
    }

    @GetMapping("api/v1/customers/{customerId}")
    public Client getClientById(
            @PathVariable("customerId") Integer customerId) {
        List<Client> customers = new ArrayList<Client>();
        return customers.stream().
                filter(customer -> customer.id.equals(customerId))
                .findFirst()
                .orElseThrow(
                        () -> (new IllegalArgumentException
                                ("customer with id[%d] not existant".formatted(customerId))));

    }
}
