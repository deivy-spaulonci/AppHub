package com.br;

import java.math.BigInteger;

public record CidadeDto(
        BigInteger id,
        String ibgeCod,
        String nome,
        String uf
        ){}
