package com.br.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.br.entity.Despesa}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaResponseDTO implements Serializable {
    BigDecimal valor;
    FormaPagamentoResponseDTO formaPagamento;
    LocalDate dataPagamento;
    String obs;
    TipoDespesaResponseDTO tipoDespesa;
    FornecedorResponseDTO fornecedor;
}