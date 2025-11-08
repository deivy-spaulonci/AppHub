package com.br.dto.response;

import com.br.entity.TipoConta;
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
public class TipoContaResponseDTO implements Serializable {
    String nome;
    Boolean cartaoCredito;
    Boolean ativo;
}