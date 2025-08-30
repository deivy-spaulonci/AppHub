package com.br.dto;

import com.br.entity.Parametro;
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
 * DTO for {@link Parametro}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametroDTO implements Serializable {
    @NotNull(message = "id paramentro inválido!")
    BigInteger id;
    @NotNull(message = "chave do paramentro inválido!")
    @Size(message = "chave do paramentro muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "chave do paramentro inválido!")
    @NotBlank(message = "chave do paramentro inválido!")
    String chave;
    @NotNull(message = "Valor do paramentro inválido!")
    @Size(message = "Valor do paramento muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "Valor do paramentro inválido!")
    @NotBlank(message = "Valor do paramentro inválido!")
    String valor;
}