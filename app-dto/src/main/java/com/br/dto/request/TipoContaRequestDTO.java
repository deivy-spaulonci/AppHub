package com.br.dto.request;

import com.br.entity.TipoConta;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link TipoConta}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoContaRequestDTO implements Serializable {

    @NotBlank(message = "Nome inválido!")
    String nome;

    Boolean cartaoCredito;

    Boolean ativo;
}