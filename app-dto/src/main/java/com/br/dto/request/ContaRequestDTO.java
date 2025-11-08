package com.br.dto.request;

import com.br.entity.Conta;
import com.br.entity.ContaStatus;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoConta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * DTO for {@link Conta}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDTO implements Serializable {

    @NotBlank(message = "Código Barras da conta inválida!")
    String codigoBarra;

    @NotNull(message = "Tipo da conta inválido!")
    TipoContaRequestDTO tipoConta;

    @NotNull(message = "Emissão conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate emissao;

    @NotNull(message = "Vencimento conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vencimento;

    Integer parcela;

    Integer totalParcela;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    BigDecimal valor;

    FormaPagamentoRequestDTO formaPagamento;

    LocalDate dataPagamento;

    String obs;

    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    BigDecimal multa;

    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    BigDecimal desconto;

    String titulo;

    String comprovante;
}