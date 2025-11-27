package com.br;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

@SpringBootApplication
//@EnableScheduling abilita o schedluing
public class AppApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(AppApiApplication.class, args);

    }
//
//    public static void keyGenerator() {
//        // Gera uma chave HMAC SHA-256 segura (256 bits)
//        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//        // Codifica a chave para Base64 para que possa ser lida como String
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//
//        System.out.println("Sua Chave Secreta JWT Segura: " + secretString);
//    }
}
