package com.br.dto;

import com.br.entity.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO for {@link TipoConta}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoContaDto implements Serializable {
    @NotNull(message = "nome tipo conta inválido!")
    @Size(message = "nome tipo conta muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "nome tipo conta inválido!")
    @NotBlank(message = "nome tipo conta inválido!")
    String nome;
    @NotNull(message = "id do tipo conta inválido!")
    BigInteger id;
    Boolean cartaoCredito;
    Boolean ativo;
}