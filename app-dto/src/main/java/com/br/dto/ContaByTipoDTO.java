package com.br.dto;

import com.br.entity.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaByTipoDTO {
    private TipoConta tipoConta;
    private BigDecimal valor;
}
