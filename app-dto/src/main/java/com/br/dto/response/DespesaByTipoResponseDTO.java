package com.br.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaByTipoResponseDTO {
    private String nomeTipoDespesa;
    private BigDecimal subTotal;
}
