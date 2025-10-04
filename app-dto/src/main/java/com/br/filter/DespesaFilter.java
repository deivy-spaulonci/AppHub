package com.br.filter;

import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class DespesaFilter {
    private Long id;
    private TipoDespesa tipoDespesa;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Fornecedor fornecedor;
    private FormaPagamento formaPagamento;
}
