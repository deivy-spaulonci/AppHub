package com.br.dao;

import com.br.dao.generic.AbstractDAO;
import com.br.dao.generic.ConnectionFactory;
import com.br.entity.Cidade;
import com.br.entity.Despesa;
import com.br.entity.Fornecedor;
import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class FornecedorDAO extends AbstractDAO<Fornecedor> {
    private final String SELECT_ALL = "SELECT * FROM FORNECEDOR F INNER JOIN CIDADE C ON C.IBGE_COD = F.IBGE_COD";
    @Override
    protected String getTableName() {
        return "FORNECEDOR";
    }

    @Override
    protected Fornecedor mapResultSet(ResultSet rs) throws SQLException {
//        Cidade cidade = Cidade.builder()
//                .id(new BigInteger(String.valueOf(rs.getInt("C.ID"))))
//                .ibgeCod(rs.getString("C.IBGE_COD"))
//                .nome(rs.getString("C.NOME"))
//                .uf(rs.getString("C.UF"))
//                .build();
        Fornecedor fornecedor = Fornecedor.builder()
                .id(new BigInteger(String.valueOf(rs.getInt("ID"))))
                .nome(rs.getString("NOME"))
                .razaoSocial(rs.getString("RAZAO_SOCIAL"))
                .cnpj(rs.getString("CNPJ")!=null ? rs.getString("CNPJ") : "")
                .cpf(rs.getString("CPF")!=null ? rs.getString("CPF") : "")
//                .cidade(cidade)
                .build();
        return fornecedor;
    }

    public List<Fornecedor> getFornecedorByFilter(String busca) {
        String sql = SELECT_ALL;
        if(busca !=null && !busca.trim().isEmpty())
            sql += " WHERE LOWER(F.NOME) LIKE ? OR LOWER(F.RAZAO_SOCIAL) LIKE ? OR LOWER(F.CNPJ) LIKE ? OR LOWER(F.CPF) LIKE ?";
        sql += " ORDER BY F.NOME";
        log.info(sql);
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if(busca !=null && !busca.trim().isEmpty()){
                ps.setString(1, "%"+busca+"%");
                ps.setString(2, "%"+busca+"%");
                ps.setString(3, "%"+busca+"%");
                ps.setString(4, "%"+busca+"%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;

    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Fornecedor fornecedor) throws SQLException {
//        String sql = "INSERT INTO produto (nome) VALUES (?)";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, produto.getNome());
//        return ps;
        return null;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Fornecedor fornecedor) throws SQLException {
//        String sql = "UPDATE produto SET nome = ? WHERE id = ?";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, produto.getNome());
//        ps.setInt(2, produto.getId());
//        return ps;
        return null;
    }
}
