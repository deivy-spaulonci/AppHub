package com.br.commands;

import com.br.business.service.*;
import com.br.commands.conta.CadastroContas;
import com.br.commands.conta.ConsultaConta;
import com.br.commands.despesa.CadastroDespesa;
import com.br.commands.despesa.ConsultaDespesa;
import com.br.commands.fornecedor.CadastroFornecedor;
import com.br.commands.fornecedor.ConsultaFornecedor;
import com.br.config.ShellHelper;
import com.br.filter.ContaFilter;
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

    private DefaultComponent defaultComponent;
    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;
    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    @Setter
    @Getter
    private Terminal terminal;
    @Autowired
    private FornecedorService fornecedorService;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private TipoContaService tipoContaService;
    @Autowired
    private TipoDespesaService tipoDespesaService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private ContaService contaService;

    private FornecedorComp fornecedorComp;

    @ShellMethod("Consulta Fornecedor")
    public void findFornecedor() {
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        ConsultaFornecedor consultaFornecedor = new ConsultaFornecedor(this.defaultComponent, this.shellHelper);
//        consultaFornecedor.consulta(fornecedorService);
    }


    @ShellMethod("Cadastro Fornecedor")
    public void addFornecedor() {
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
        CadastroFornecedor cadastroFornecedor = new CadastroFornecedor(this.defaultComponent, this.shellHelper);
        cadastroFornecedor.cadastro(fornecedorService, cidadeService);
    }

    @ShellMethod("Status Despesas")
    public void statusDespesa(){
        DespesaFilter despesaFilter = DespesaFilter.builder().build();
        shellHelper.printInfo("-".repeat(50));
        shellHelper.printInfo(String.format("> Registros: %-10s", despesaService.getCountDespesa()));
        shellHelper.printInfo(String.format("> Total: %-10s", Util.toCurrencyPtBr(despesaService.getSumDespesa(despesaFilter))));
        shellHelper.printInfo("-".repeat(50));
    }

//    @ShellMethod("Consulta Despesas")
//    public void despesas(){
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        ConsultaDespesa consultaDespesa = new ConsultaDespesa(this.defaultComponent,
//                this.shellHelper,
//                this.fornecedorComp);
//        consultaDespesa.consulta(despesaService, fornecedorService, tipoDespesaService, formaPagamentoService);
//    }

//    @ShellMethod("Cadastro Despesas")
//    public void addDespesa() {
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        CadastroDespesa cadastroDespesa = new CadastroDespesa(this.defaultComponent, this.shellHelper);
//        cadastroDespesa.cadastrar(tipoDespesaService,
//                formaPagamentoService,
//                despesaService,
//                fornecedorService,
//                cidadeService);
//    }

//    @ShellMethod("Consulta Contas")
//    public void contas(){
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        ConsultaConta consultaConta = new ConsultaConta(this.defaultComponent, shellHelper);
//        consultaConta.consulta(contaService, tipoContaService, formaPagamentoService);
//    }

    @ShellMethod("Cadastro Contas")
    public void addContas(){
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
        CadastroContas cadastroContas = new CadastroContas(this.defaultComponent, shellHelper);
//        cadastroContas.cadastrar(contaService, tipoContaService, formaPagamentoService, fornecedorService);
    }

    @ShellMethod("Status Contas")
    public void statusConta(){
        shellHelper.printInfo("-".repeat(50));
        ContaFilter contaFilter = ContaFilter.builder().build();
        shellHelper.printInfo(String.format("> Registros: %-10s", contaService.getCountConta()));
        shellHelper.printInfo(String.format("> " + "Total: %-10s", Util.toCurrencyPtBr(contaService.getSumConta(contaFilter))));
        shellHelper.printInfo("-".repeat(50));
    }
}
