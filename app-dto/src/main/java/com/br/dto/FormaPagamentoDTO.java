package com.br.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoDTO {
    @NotNull(message = "nome forma pagamento inválido!")
    @Size(message = "nome forma pagamento muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "nome forma pagamento inválido!")
    @NotBlank(message = "nome forma pagamento inválido!")
    String nome;
    @NotNull(message = "id do forma pagamento inválido!")
    BigInteger id;
}
