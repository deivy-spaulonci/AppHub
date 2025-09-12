package com.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling abilita o schedluing
public class AppApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApiApplication.class, args);
    }
}