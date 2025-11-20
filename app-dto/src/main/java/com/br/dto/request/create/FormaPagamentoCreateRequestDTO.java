package com.br.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoCreateRequestDTO implements Serializable {
    @NotBlank(message = "Nome da Forma Pagamento inválida!")
    private String nome;
}
