package com.br.dto.response;

import com.br.entity.TipoConta;
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
public class TipoContaResponseDTO implements Serializable {
    BigInteger id;
    String nome;
    Boolean cartaoCredito;
    Boolean ativo;
}