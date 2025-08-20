package com.br.dao;

import com.br.dao.generic.AbstractDAO;
import com.br.entity.Despesa;
import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import com.br.filter.DespesaFilter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
@Log4j2
public class DespesaDAO extends AbstractDAO<Despesa> {
    @Override
    protected String getTableName() {
        return "DESPESA";
    }

    private final String SELECT_ALL = "SELECT D.*, TD.NOME AS NOME_DESPPESA, " +
            "FP.NOME AS NOME_FORMA_PAGAMENTO, " +
            "F.NOME AS NOME_FORNECEDOR FROM DESPESA D " +
            "INNER JOIN TIPO_DESPESA TD ON TD.ID = D.ID_TIPO_DESPESA " +
            "INNER JOIN FORMA_PAGAMENTO FP ON FP.ID = D.ID_FORMA_PAGAMENTO " +
            "INNER JOIN FORNECEDOR F ON F.ID = D.ID_FORNECEDOR ";


    @Override
    protected Despesa mapResultSet(ResultSet rs) throws SQLException {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(new BigInteger(rs.getString("ID_TIPO_DESPESA")));
        tipoDespesa.setNome(rs.getString("NOME_DESPPESA"));

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(new BigInteger(rs.getString("ID_FORMA_PAGAMENTO")));
        formaPagamento.setNome(rs.getString("NOME_FORMA_PAGAMENTO"));

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(new BigInteger(rs.getString("ID_FORNECEDOR")));
        fornecedor.setNome(rs.getString("NOME_FORNECEDOR"));

        Despesa despesa = Despesa.builder()
                .id(new BigInteger(rs.getString("ID")))
                .tipoDespesa(tipoDespesa)
                .fornecedor(fornecedor)
                .formaPagamento(formaPagamento)
                .obs(rs.getString("OBS"))
                .valor(new BigDecimal(rs.getString("VALOR")))
                .dataPagamento(LocalDate.parse(rs.getString("DATA_PAGAMENTO")))
                .build();
        return despesa;
    }

    public List<Despesa> getDespesasByFilter(DespesaFilter despesaFilter) {
        String sql = SELECT_ALL + " WHERE 1=1 " ;

        if(despesaFilter != null) {
            if(despesaFilter.getTipoDespesa() != null)
                sql += " AND D.ID_TIPO_DESPESA = "+despesaFilter.getTipoDespesa().getId();
            if(despesaFilter.getFormaPagamento() != null)
                sql += " AND D.ID_FORMA_PAGAMENTO = "+despesaFilter.getFormaPagamento().getId();
            if(despesaFilter.getFornecedor() != null && despesaFilter.getFornecedor().getId()!=null)
                sql += " AND D.ID_FORNECEDOR = "+despesaFilter.getFornecedor().getId();
            if(despesaFilter.getDataInicial() != null && despesaFilter.getDataFinal() != null) {
                sql += " AND D.DATA_PAGAMENTO BETWEEN '%s' AND '%s' ".formatted(despesaFilter.getDataInicial().toString(),
                                despesaFilter.getDataFinal().toString());
                System.out.println(sql);
            }
        }

        sql += " ORDER BY D.DATA_PAGAMENTO DESC";
        return listGeneric(sql);
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Despesa despesa) throws SQLException {

        ResultSet rsNextVal = conn.prepareStatement("SELECT NEXTVAL('DESPESA_SEQ') AS ID").executeQuery();
        int id = 0;
        if (rsNextVal.next()) {
            id = rsNextVal.getInt(1);
            log.info("Proximo id disponível: " + id);
        }

        String sql = "INSERT INTO DESPESA (ID, ID_TIPO_DESPESA, ID_FORNECEDOR, ID_FORMA_PAGAMENTO, DATA_PAGAMENTO, VALOR, OBS) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.setInt(2, despesa.getTipoDespesa().getId().intValue());
        ps.setInt(3, despesa.getFornecedor().getId().intValue());
        ps.setInt(4, despesa.getFormaPagamento().getId().intValue());
        ps.setString(5, despesa.getDataPagamento().toString());
        ps.setString(6, despesa.getValor().toString());
        ps.setString(7, despesa.getObs());
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Despesa despesa) throws SQLException {
        String sql = "UPDATE DESPESA SET ID_TIPO_DESPESA = ?, ID_FORNECEDOR = ?, ID_FORMA_PAGAMENTO = ?, DATA_PAGAMENTO = ? , VALOR = ?, OBS = ?  WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, despesa.getId().intValue());
        ps.setInt(2, despesa.getTipoDespesa().getId().intValue());
        ps.setInt(3, despesa.getFornecedor().getId().intValue());
        ps.setInt(4, despesa.getFormaPagamento().getId().intValue());
        ps.setString(5, despesa.getDataPagamento().toString());
        ps.setString(6, despesa.getValor().toString());
        ps.setString(7, despesa.getObs());
        return ps;
    }

//    public void criarTabela() {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS produto (
//                id INT AUTO_INCREMENT PRIMARY KEY,
//                nome VARCHAR(100)
//            )
//        """;
//        try (Connection conn = ConnectionFactory.getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
