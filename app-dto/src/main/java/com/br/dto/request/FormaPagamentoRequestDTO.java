package com.br.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoRequestDTO  implements Serializable {
    @NotBlank(message = "Nome da Forma Pagamento inválida!")
    String nome;
}
