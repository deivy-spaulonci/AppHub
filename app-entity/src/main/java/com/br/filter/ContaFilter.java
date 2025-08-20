package com.br.filter;

import com.br.entity.ContaStatus;
import com.br.entity.TipoConta;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ContaFilter {
    private Long id;
    private TipoConta tipoConta;
    private LocalDate vencimentoInicial;
    private LocalDate vencimentoFinal;
    private LocalDate emissaoInicial;
    private LocalDate emissaoFinal;
    private ContaStatus contaStatus;

}
