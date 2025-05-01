package com.br.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDespesaDto implements Serializable {
    @NotNull(message = "nome tipo despesa inválido!")
    @Size(message = "nome tipo despesa muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "nome tipo despesa inválido!")
    @NotBlank(message = "nome tipo despesa inválido!")
    String nome;
    @NotNull(message = "id do tipo despesa inválido!")
    BigInteger id;
}
