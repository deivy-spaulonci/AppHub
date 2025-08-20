package com.br.dao;

import com.br.dao.generic.AbstractDAO;
import com.br.entity.FormaPagamento;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FormaPagamentoDAO extends AbstractDAO<FormaPagamento> {
    @Override
    protected String getTableName() {
        return "FORMA_PAGAMENTO";
    }

    @Override
    protected FormaPagamento mapResultSet(ResultSet rs) throws SQLException {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(new BigInteger(String.valueOf(rs.getInt("ID"))));
        formaPagamento.setNome(rs.getString("NOME"));
        return formaPagamento;
    }

    public List<FormaPagamento> getFormaPagamento() {
        return listGeneric("SELECT * FROM "+getTableName()+" ORDER BY NOME");
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, FormaPagamento obj) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, FormaPagamento obj) throws SQLException {
        return null;
    }
}
