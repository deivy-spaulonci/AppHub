package com.br.commands;

import com.br.business.service.*;
import com.br.commands.fornecedor.FornecedorComp;
import com.br.config.ShellHelper;
import com.br.filter.DespesaFilter;
import com.br.util.Util;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Log4j2
@ShellComponent
public class FinanceiroCommands extends AbstractShellComponent {

    @Setter
    @Getter
    private Terminal terminal;
    private DefaultComponent defaultComponent;
    private ComponentFlow.Builder componentFlowBuilder;
    private ShellHelper shellHelper;
    private FornecedorService fornecedorService;
    private CidadeService cidadeService;
    private DespesaService despesaService;
    private TipoContaService tipoContaService;
    private TipoDespesaService tipoDespesaService;
    private FormaPagamentoService formaPagamentoService;
    private ContaService contaService;
    private FornecedorComp fornecedorComp;

    @Autowired
    public FinanceiroCommands(ComponentFlow.Builder componentFlowBuilder,
                              ShellHelper shellHelper,
                              Terminal terminal,
                              FornecedorService fornecedorService,
                              CidadeService cidadeService,
                              DespesaService despesaService,
                              TipoContaService tipoContaService,
                              TipoDespesaService  tipoDespesaService,
                              FormaPagamentoService formaPagamentoService,
                              ContaService contaService) {
        this.componentFlowBuilder = componentFlowBuilder;
        this.shellHelper = shellHelper;
        this.terminal = terminal;
        this.fornecedorService = fornecedorService;
        this.cidadeService = cidadeService;
        this.despesaService = despesaService;
        this.tipoContaService = tipoContaService;
        this.tipoDespesaService = tipoDespesaService;
        this.formaPagamentoService = formaPagamentoService;
        this.contaService = contaService;
//        this.fornecedorComp = new FornecedorComp(terminal, getTemplateExecutor(), getResourceLoader());
    }

    @ShellMethod("Status Despesas")
    public void statusDespesa(){
        DespesaFilter despesaFilter = DespesaFilter.builder().build();
        shellHelper.prInfo("-".repeat(50));
        shellHelper.prInfo(String.format("> Registros: %-10s", despesaService.getCountDespesa()));
        shellHelper.prInfo(String.format("> Total:     %-10s", Util.toCurrencyPtBr(despesaService.getSumDespesa(despesaFilter))));
        shellHelper.prInfo("-".repeat(50));
    }


//    @ShellMethod("Consulta Fornecedor")
//    public void findFornecedor() {
////        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
////        ConsultaFornecedor consultaFornecedor = new ConsultaFornecedor(this.defaultComponent, this.shellHelper);
////        consultaFornecedor.consulta(fornecedorService);
//    }
//
//
//    @ShellMethod("Cadastro Fornecedor")
//    public void addFornecedor() {
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        CadastroFornecedor cadastroFornecedor = new CadastroFornecedor(this.defaultComponent, this.shellHelper);
//        cadastroFornecedor.cadastro(fornecedorService, cidadeService);
//    }
//
//
//
////    @ShellMethod("Consulta Despesas")
////    public void despesas(){
////        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
////        ConsultaDespesa consultaDespesa = new ConsultaDespesa(this.defaultComponent,
////                this.shellHelper,
////                this.fornecedorComp);
////        consultaDespesa.consulta(despesaService, fornecedorService, tipoDespesaService, formaPagamentoService);
////    }
//
////    @ShellMethod("Cadastro Despesas")
////    public void addDespesa() {
////        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
////        CadastroDespesa cadastroDespesa = new CadastroDespesa(this.defaultComponent, this.shellHelper);
////        cadastroDespesa.cadastrar(tipoDespesaService,
////                formaPagamentoService,
////                despesaService,
////                fornecedorService,
////                cidadeService);
////    }
//
////    @ShellMethod("Consulta Contas")
////    public void contas(){
////        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
////        ConsultaConta consultaConta = new ConsultaConta(this.defaultComponent, shellHelper);
////        consultaConta.consulta(contaService, tipoContaService, formaPagamentoService);
////    }
//
//    @ShellMethod("Cadastro Contas")
//    public void addContas(){
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        CadastroContas cadastroContas = new CadastroContas(this.defaultComponent, shellHelper);
////        cadastroContas.cadastrar(contaService, tipoContaService, formaPagamentoService, fornecedorService);
//    }
//
//    @ShellMethod("Status Contas")
//    public void statusConta(){
//        shellHelper.printInfo("-".repeat(50));
//        ContaFilter contaFilter = ContaFilter.builder().build();
//        shellHelper.printInfo(String.format("> Registros: %-10s", contaService.getCountConta()));
//        shellHelper.printInfo(String.format("> " + "Total: %-10s", Util.toCurrencyPtBr(contaService.getSumConta(contaFilter))));
//        shellHelper.printInfo("-".repeat(50));
//    }
}
