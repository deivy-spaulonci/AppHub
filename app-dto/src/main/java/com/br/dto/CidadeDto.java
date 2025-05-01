package com.br.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDto implements Serializable {
    @NotNull(message = "id da Cidade vazio!")
    private BigInteger id;
    @NotNull(message = "ibgeCod da Cidade vazio!")
    private String ibgeCod;
    @NotNull(message = "Nome da Cidade vazio!")
    private String nome;
    @NotNull(message = "Uf da Cidade vazio!")
    private String uf;
}
