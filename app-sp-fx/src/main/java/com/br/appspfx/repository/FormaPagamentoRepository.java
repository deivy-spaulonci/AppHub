package com.br.appspfx.repository;

import com.br.entity.FormaPagamento;
import com.br.entity.TipoDespesa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FormaPagamentoRepository {
    private final JdbcTemplate jdbcTemplate;

    public FormaPagamentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FormaPagamento> listarFormaPagamento() {
        String sql = "SELECT * FROM FORMA_PAGAMENTO ORDER BY NOME";
        return jdbcTemplate.query(sql, new RowMapper<FormaPagamento>() {
            @Override
            public FormaPagamento mapRow(ResultSet rs, int rowNum) throws SQLException {
                FormaPagamento formaPagamento = new FormaPagamento();
                formaPagamento.setId(new BigInteger(rs.getString("id")));
                formaPagamento.setNome(rs.getString("nome"));
                return formaPagamento;
            }
        });
    }
}
