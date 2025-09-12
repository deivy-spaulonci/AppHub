package com.br.teste;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableRabbit
@SpringBootApplication
public class TesteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TesteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
