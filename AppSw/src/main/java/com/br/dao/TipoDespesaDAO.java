package com.br.dao;

import com.br.dao.generic.AbstractDAO;
import com.br.dao.generic.ConnectionFactory;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoDespesaDAO extends AbstractDAO<TipoDespesa> {
    @Override
    protected String getTableName() {
        return "TIPO_DESPESA";
    }

    @Override
    protected TipoDespesa mapResultSet(ResultSet rs) throws SQLException {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(new BigInteger(String.valueOf(rs.getInt("ID"))));
        tipoDespesa.setNome(rs.getString("NOME"));
        return tipoDespesa;
    }

    public List<TipoDespesa> getTipoDespesa() {
        return listGeneric("SELECT * FROM "+getTableName()+" ORDER BY NOME");
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, TipoDespesa obj) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, TipoDespesa obj) throws SQLException {
        return null;
    }

}
