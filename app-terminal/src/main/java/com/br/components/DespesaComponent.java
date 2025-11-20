package com.br.components;

import com.br.business.service.DespesaService;
import com.br.commands.DefaultComponent;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;

@Getter
@ShellComponent
public class DespesaComponent {

    private TipoDespesaComponent tipoDespesaComponent;
    private FormaPagamentoComponent formaPagamentoComponent;
    private FornecedorComponent fornecedorComponent;
    private DespesaService despesaService;
    private final DefaultComponent defaultComponent;

    @Autowired
    public DespesaComponent(DefaultComponent defaultComponent,
                                DespesaService despesaService,
                            TipoDespesaComponent tipoDespesaComponent,
                            FornecedorComponent fornecedorComponent,
                            FormaPagamentoComponent formaPagamentoComponent) {
        this.defaultComponent = defaultComponent;
        this.despesaService = despesaService;
        this.tipoDespesaComponent = tipoDespesaComponent;
        this.formaPagamentoComponent = formaPagamentoComponent;
        this.fornecedorComponent = fornecedorComponent;
    }
}
