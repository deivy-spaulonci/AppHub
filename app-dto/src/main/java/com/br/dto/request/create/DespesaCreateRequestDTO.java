package com.br.dto.request.create;

import com.br.dto.ref.FormaPagamentoRefDTO;
import com.br.dto.ref.FornecedorRefDTO;
import com.br.dto.ref.TipoDespesaRefDTO;
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
public class DespesaCreateRequestDTO implements Serializable {

    @NotNull(message = "Tipo da despesa inválido!")
    private TipoDespesaRefDTO tipoDespesa;

    @NotNull(message = "Fornecedor inválido!")
    private FornecedorRefDTO fornecedor;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @NotNull(message = "Data Pgto da despesa inválida!")
    private LocalDate dataPagamento;

    @NotNull(message = "Forma Pgto da despesa inválida!")
    private FormaPagamentoRefDTO formaPagamento;

    private String obs;
}