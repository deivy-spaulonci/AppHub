package com.br.dao.generic;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class AbstractDAO<T> implements GenericDAO<T> {

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet rs) throws SQLException;

    protected abstract PreparedStatement prepareInsert(Connection conn, T obj) throws SQLException;

    protected abstract PreparedStatement prepareUpdate(Connection conn, T obj) throws SQLException;

    @Override
    public void inserir(T obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = prepareInsert(conn, obj)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(T obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = prepareUpdate(conn, obj)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public T buscarPorId(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    public T findGeneric(String sql) {
        log.info(sql);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
                while (rs.next()) {
                    return mapResultSet(rs);
                }
        } catch (SQLException e) {
            log.error(e);
        }
        return null;
    }

    public List<T> listGeneric(String sql) {
        List<T> lista = new ArrayList<>();
        log.info(sql);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<T> listarTodos() {
        List<T> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        log.info(sql);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
        return lista;
    }
}

