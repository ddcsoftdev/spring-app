package com.ddcsoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public Greet greet() {
        String[] s = {"dpg", "cat", "fish"};
        return new Greet("hello", 21, s);
    }

    public record Greet(String item, int age, String[] city) {

    }

}
