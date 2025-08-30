package com.br.dto;

import com.br.entity.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaByTipoDTO {

    private TipoDespesa tipoDespesa;

    private BigDecimal subTotal;
}
