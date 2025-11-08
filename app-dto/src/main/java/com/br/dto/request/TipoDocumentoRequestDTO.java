package com.br.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class TipoDocumentoRequestDTO implements Serializable {

    @Size(message = "Nome do tipo muito longo!", min = 3, max = 255)
    @NotBlank(message = "Nome tipo documento vazio!")
    String nome;

    @NotNull(message = "Id tipo documento vazio!")
    BigInteger id;
}