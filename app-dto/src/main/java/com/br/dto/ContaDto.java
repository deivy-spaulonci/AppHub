package com.br.dto;

import com.br.entity.Conta;
import com.br.entity.Fatura;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoConta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Conta}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDto implements Serializable {

    @NotNull(message = "Valor da conta vazio!")
    @Min(message = "Valor da conta vazio!", value = 0)
    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    BigDecimal valor;

    FormaPagamento formaPagamento;

    LocalDate dataPagamento;

    String obs;

    BigInteger id;

    @NotNull(message = "Tipo da conta inválido!")
    TipoConta tipoConta;

    @NotNull(message = "codigo barras da conta inválida!")
    @NotEmpty(message = "codigo barras da conta inválida!")
    @NotBlank(message = "codigo barras da conta inválida!")
    String codigoBarra;

    @NotNull(message = "emissao conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate emissao;

    @NotNull(message = "vencimento conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vencimento;

    Integer parcela;

    Integer totalParcela;

    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    BigDecimal multa;

    String titulo;

    String comprovante;

    List<Fatura> faturas;

    String status;
}