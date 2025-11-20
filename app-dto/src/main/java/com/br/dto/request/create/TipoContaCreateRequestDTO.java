package com.br.dto.request.create;

import com.br.entity.TipoConta;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link TipoConta}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoContaCreateRequestDTO implements Serializable {
    @NotBlank(message = "Nome do Tipo de Conta  inválido!")
    private String nome;
    private Boolean cartaoCredito;
    private Boolean ativo;
}