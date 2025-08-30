package com.br.dto;

import com.br.entity.FormaPagamento;
import com.br.entity.TipoDespesa;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * DTO for {@link com.br.entity.Despesa}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO implements Serializable {
    @NotNull(message = "Valor da despesa vazio!")
    @Min(message = "Valor da despesa vazio!", value = 0)
    @Digits(message = "Valor da despesa inválido!", integer = 10, fraction = 2)
    BigDecimal valor;
    @NotNull(message = "Forma Pgto da despesa inválida!")
    FormaPagamento formaPagamento;
    @NotNull(message = "Data Pgto da despesa inválida!")
    LocalDate dataPagamento;
    String obs;
    BigInteger id;
    @NotNull(message = "Tipo da despesa inválido!")
    TipoDespesa tipoDespesa;
    @NotNull(message = "Fornecedor da despesa inválido!")
    FornecedorDTO fornecedor;

    LocalDate lancamento;
}