package com.br.dao;

import com.br.dao.generic.AbstractDAO;
import com.br.entity.Cidade;
import com.br.entity.FormaPagamento;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CidadeDAO extends AbstractDAO<Cidade> {
    @Override
    protected String getTableName() {
        return "CIDADE";
    }

    @Override
    protected Cidade mapResultSet(ResultSet rs) throws SQLException {
        Cidade cidade = new Cidade();
        cidade.setId(new BigInteger(String.valueOf(rs.getInt("ID"))));
        cidade.setNome(rs.getString("NOME"));
        cidade.setIbgeCod(rs.getString("IBGE_COD"));
        cidade.setUf(rs.getString("UF"));
        return cidade;
    }

    public List<Cidade> getCidade(String uf) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE 1=1 ";
        if (!Objects.isNull(uf) || !uf.isEmpty())
            sql += " AND UF = '%s' ".formatted(uf);

        sql += " ORDER BY NOME";
        return listGeneric(sql);
    }

    public Cidade buscarPorCodIbge(String ibgeCod) {
        String sql = "SELECT * FROM " + getTableName();
        if (!Objects.isNull(ibgeCod) || !ibgeCod.isEmpty())
            sql += " WHERE IBGE_COD = '%s' ".formatted(ibgeCod);

        return findGeneric(sql);
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Cidade obj) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Cidade obj) throws SQLException {
        return null;
    }


}
