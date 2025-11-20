package com.br.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.br.entity.TipoDocumento}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoCreateRequestDTO implements Serializable {

    @Size(message = "Nome do tipo muito longo!", min = 3, max = 255)
    @NotBlank(message = "Nome tipo documento vazio!")
    private String nome;

}