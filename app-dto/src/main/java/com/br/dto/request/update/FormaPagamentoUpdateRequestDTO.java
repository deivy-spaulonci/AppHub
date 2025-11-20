package com.br.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoUpdateRequestDTO implements Serializable {
    @NotNull
    private BigInteger id;
    @NotBlank(message = "Nome da Forma Pagamento inválida!")
    private String nome;
}
