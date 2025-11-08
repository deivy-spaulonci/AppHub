package com.br.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeResponseDTO implements Serializable {
    private String ibgeCod;
    private String nome;
    private String uf;
}
