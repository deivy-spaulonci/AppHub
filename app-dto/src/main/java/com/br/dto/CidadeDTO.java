package com.br.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO implements Serializable {
    private BigInteger id;
    private String ibgeCod;
    private String nome;
    private String uf;
}
