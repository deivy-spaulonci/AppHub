package com.br.dto.ref;

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
public class CidadeRefDTO  implements Serializable {
    private BigInteger id;
    @NotBlank(message = "Código do IBGE inválido!")
    private String ibgeCod;
}
