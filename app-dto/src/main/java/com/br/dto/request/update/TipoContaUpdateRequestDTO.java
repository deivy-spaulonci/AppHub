package com.br.dto.request.update;

import com.br.entity.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO for {@link TipoConta}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoContaUpdateRequestDTO implements Serializable {
    @NotNull
    private BigInteger id;
    @NotBlank(message = "Nome do Tipo de Conta  inválido!")
    private String nome;
    private Boolean cartaoCredito;
    private Boolean ativo;
}