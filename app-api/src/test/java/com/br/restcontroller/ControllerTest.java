package com.br.restcontroller;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@SpringBootTest
public class ControllerTest {

    @Test
    public void testUpladMega(){


//        String origem = "/home/d31vy/Documentos/endereco.txt";
//        String destino = "/home/d31vy/MEGA/endereco.txt";
//        try {
//            copiarArquivo(origem, destino);
//            System.out.println("Arquivo copiado com sucesso!");
//        } catch (IOException e) {
//            System.err.println("Erro ao copiar o arquivo: " + e.getMessage());
//        }
    }

    private static void copiarArquivo(String origem, String destino) throws IOException {
        Path origemPath = Paths.get(origem);
        Path destinoPath = Paths.get(destino);
        Files.copy(origemPath, destinoPath);
    }


    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        log.info("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }


}
