package com.br.dto;

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
 * DTO for {@link com.br.entity.TipoDocumento}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoDTO implements Serializable {
    @NotNull(message = "Nome tipo documento vazio!")
    @Size(message = "Nome do tipo muito longo!", min = 3, max = 255)
    @NotEmpty(message = "Nome tipo documento vazio!")
    @NotBlank(message = "Nome tipo documento vazio!")
    String nome;
    @NotNull(message = "Id tipo documento vazio!")
    BigInteger id;
}