package com.br.appspfx.repository;

import com.br.entity.TipoDespesa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TipoDespesaRepository {
    private final JdbcTemplate jdbcTemplate;

    public TipoDespesaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public void criarPessoa(String nome, int idade) {
//        String sql = "INSERT INTO pessoas (nome, idade) VALUES (?, ?)";
//        jdbcTemplate.update(sql, nome, idade);
//    }

    public List<TipoDespesa> listarTipoDespesa() {
        String sql = "SELECT * FROM tipo_despesa ORDER BY nome";
        return jdbcTemplate.query(sql, new RowMapper<TipoDespesa>() {
            @Override
            public TipoDespesa mapRow(ResultSet rs, int rowNum) throws SQLException {
                TipoDespesa tipoDespesa = new TipoDespesa();
                tipoDespesa.setId(new BigInteger(rs.getString("id")));
                tipoDespesa.setNome(rs.getString("nome"));
                return tipoDespesa;
            }
        });
    }
}
