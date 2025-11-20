package com.br.dto.request.create;

import com.br.entity.Parametro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Parametro}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametroCreateRequestDTO implements Serializable {

    @Size(message = "chave do paramentro muito extenso!", min = 3, max = 255)
    @NotBlank(message = "chave do paramentro inválido!")
    private String chave;

    @Size(message = "Valor do paramento muito extenso!", min = 3, max = 255)
    @NotBlank(message = "Valor do paramentro inválido!")
    private String valor;
}