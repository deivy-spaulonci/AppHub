package com.br.dto.request.create;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDespesaCreateRequestDTO implements Serializable  {
    private static final long serialVersionUID = 1L;
    @NotEmpty
    private ArrayList<DespesaCreateRequestDTO> despesaCreateRequestDTOS;
}
