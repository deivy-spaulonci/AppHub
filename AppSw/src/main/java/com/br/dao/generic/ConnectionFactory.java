package com.br.dao.generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:h2:tcp://localhost:9092/~/h2/bin/data/app-db"; // ou jdbc:h2:mem:testdb
    private static final String USER = "deivy";
    private static final String PASSWORD = "duocore";

    static {
        try {
            Class.forName("org.h2.Driver"); // Opcional com JDBC 4+, mas bom para garantir
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do H2 não encontrado.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
