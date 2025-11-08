package com.br.dto.response;

import com.br.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Conta}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaResponseDTO implements Serializable {
    String codigoBarra;
    TipoContaResponseDTO tipoConta;
    LocalDate emissao;
    LocalDate vencimento;
    Integer parcela;
    Integer totalParcela;
    BigDecimal valor;
    FormaPagamentoResponseDTO formaPagamento;
    LocalDate dataPagamento;
    BigDecimal multa;
    BigDecimal desconto;
    String obs;
    String titulo;
    String comprovante;

    public int getIntStatus() {
        if(getVencimento()!=null && getDataPagamento()==null){
            if(getVencimento().isAfter(LocalDate.now()))
                return 1;
            if(getVencimento().isEqual(LocalDate.now()))
                return 0;
            if(getVencimento().isBefore(LocalDate.now()))
                return -1;
        }
        return 2;
    }

    public String getStatus() {
        return switch (getIntStatus()) {
            case 1 -> ContaStatus.ABERTO.getNome();
            case 0 -> ContaStatus.HOJE.getNome();
            case -1 -> ContaStatus.ATRASADO.getNome();
            case 2 -> ContaStatus.PAGO.getNome();
            default -> ContaStatus.PAGO.getNome();
        };
    }

    public BigDecimal getValorPago() {
        if(dataPagamento!=null && formaPagamento!=null)
            return getValor().add(multa).subtract(desconto);
        return BigDecimal.ZERO;

    }
}