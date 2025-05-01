package com.br.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class LoteDespesaDto  implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Valid
    private ArrayList<DespesaDto> despesaDtos;
}
