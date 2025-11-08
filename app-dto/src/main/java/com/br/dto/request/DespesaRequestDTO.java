package com.br.dto.request;

import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoDespesa;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class DespesaRequestDTO implements Serializable {

    @NotNull(message = "Tipo da despesa inválido!")
    TipoDespesaRequestDTO tipoDespesa;

    @NotNull(message = "Fornecedor inválido!")
    FornecedorRequestDTO fornecedor;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    BigDecimal valor;

    @NotNull(message = "Data Pgto da despesa inválida!")
    LocalDate dataPagamento;

    @NotNull(message = "Forma Pgto da despesa inválida!")
    FormaPagamentoRequestDTO formaPagamento;

    String obs;
}