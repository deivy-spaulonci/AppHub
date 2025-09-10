package com.br.appspfx.repository;

import com.br.entity.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class DespesaRepository {
    private String SQL = "SELECT D.ID, D.ID_TIPO_DESPESA, T.NOME, D.DATA_PAGAMENTO, " +
            " D.ID_FORNECEDOR, F.NOME AS FORNECEDOR_NOME, " +
            " D.ID_FORMA_PAGAMENTO, FP.NOME AS FORMA_PAGAMENTO_NOME, "+
            " D.VALOR, D.OBS FROM DESPESA D " +
            " INNER JOIN TIPO_DESPESA T ON T.ID = D.ID_TIPO_DESPESA " +
            " INNER JOIN FORMA_PAGAMENTO FP ON FP.ID = D.ID_FORMA_PAGAMENTO " +
            " INNER JOIN FORNECEDOR F ON F.ID = D.ID_FORNECEDOR " +
            " ORDER BY D.DATA_PAGAMENTO DESC";

    private final JdbcTemplate jdbcTemplate;

    public DespesaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Despesa> listarDespesa() {
        return jdbcTemplate.query(SQL, new RowMapper<Despesa>() {
            @Override
            public Despesa mapRow(ResultSet rs, int rowNum) throws SQLException {
                Despesa despesa = new Despesa();
                despesa.setId(new BigInteger(rs.getString("ID")));
                TipoDespesa tipoDespesa = new TipoDespesa();
                tipoDespesa.setId(new BigInteger(rs.getString("ID_TIPO_DESPESA")));
                tipoDespesa.setNome(rs.getString("NOME"));
                despesa.setTipoDespesa(tipoDespesa);
                despesa.setDataPagamento(LocalDate.parse(rs.getString("DATA_PAGAMENTO")));
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(new BigInteger(rs.getString("ID_FORNECEDOR")));
                fornecedor.setNome(rs.getString("FORNECEDOR_NOME"));
                despesa.setFornecedor(fornecedor);
                FormaPagamento  formaPagamento = new FormaPagamento();
                formaPagamento.setId(new BigInteger(rs.getString("ID_FORMA_PAGAMENTO")));
                formaPagamento.setNome(rs.getString("FORMA_PAGAMENTO_NOME"));
                despesa.setFormaPagamento(formaPagamento);
                despesa.setValor(rs.getBigDecimal("VALOR"));
                despesa.setObs(rs.getString("OBS"));
                return despesa;
            }
        });
    }
}
