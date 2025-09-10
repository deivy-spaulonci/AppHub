package com.br.appspfx.repository;

import com.br.entity.Despesa;
import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class FornecedorRepository {

    private final JdbcTemplate jdbcTemplate;

    public FornecedorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Fornecedor> findAll(String txt) {
        String SQL = "SELECT * FROM FORNECEDOR F ";
        if(txt !=null && !txt.trim().isEmpty()){
            txt = "'%"+txt+"%'";
            SQL += " WHERE LOWER(F.NOME) LIKE %s OR LOWER(F.RAZAO_SOCIAL) LIKE %s OR LOWER(F.CNPJ) LIKE %s OR LOWER(F.CPF) LIKE %s ".formatted(txt,txt,txt,txt);
        }
        SQL += " ORDER BY F.NOME";
        System.out.println("SQL: "+SQL);

        return jdbcTemplate.query(SQL, new RowMapper<Fornecedor>() {
            @Override
            public Fornecedor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(new BigInteger(rs.getString("ID")));
                fornecedor.setNome(rs.getString("NOME"));
                fornecedor.setCpf(rs.getString("CPF"));
                fornecedor.setRazaoSocial(rs.getString("RAZAO_SOCIAL"));
                fornecedor.setCnpj(rs.getString("CNPJ"));
                return fornecedor;
            }
        });
    }
}
