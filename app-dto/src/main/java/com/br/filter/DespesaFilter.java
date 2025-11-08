package com.br.filter;

import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Builder
@Setter
@Getter
public class DespesaFilter {
    private BigInteger id;
    private BigInteger idTipoDespesa;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private BigInteger idFornecedor;
    private BigInteger idFormaPagamento;
}
