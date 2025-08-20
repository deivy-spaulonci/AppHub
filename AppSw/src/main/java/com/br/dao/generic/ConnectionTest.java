package com.br.dao.generic;

import java.sql.*;

public class ConnectionTest {

    public static void main(String[] args) {

        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão obtida com sucesso!");

            String createTable = "CREATE TABLE IF NOT EXISTS produto (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100))";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTable);
            }

            String insert = "INSERT INTO produto (nome) VALUES (?)";
            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setString(1, "Notebook");
                ps.executeUpdate();
            }

            String select = "SELECT * FROM produto";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(select)) {

                while (rs.next()) {
                    System.out.println("Produto: " + rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
