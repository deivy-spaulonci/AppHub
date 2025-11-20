package com.br.dto.request.create;

import com.br.dto.ref.FormaPagamentoRefDTO;
import com.br.dto.ref.TipoContaRefDTO;
import com.br.entity.Conta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Conta}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaCreateRequestDTO implements Serializable {

    @NotBlank(message = "Código Barras da conta inválida!")
    private String codigoBarra;

    @NotNull(message = "Tipo da conta inválido!")
    private TipoContaRefDTO tipoConta;

    @NotNull(message = "Emissão conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate emissao;

    @NotNull(message = "Vencimento conta inválida!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimento;

    private Integer parcela;

    private Integer totalParcela;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    private FormaPagamentoRefDTO formaPagamento;

    private LocalDate dataPagamento;

    private String obs;

    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    private BigDecimal multa;

    @Digits(message = "Valor da conta inválido!", integer = 10, fraction = 2)
    private BigDecimal desconto;

    private String titulo;

    private String comprovante;
}