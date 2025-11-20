package com.br.commands;

import com.br.commands.despesa.CadastroDespesa;
import com.br.commands.despesa.ConsultaDespesa;
import com.br.commands.fornecedor.CadastroFornecedor;
import com.br.commands.fornecedor.ConsultaFornecedor;
import com.br.components.DespesaComponent;
import com.br.components.FornecedorComponent;
import lombok.extern.log4j.Log4j2;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.control.BoxView;
import org.springframework.shell.geom.Rectangle;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Log4j2
@ShellComponent
public class FinanceiroCommands{

    private DespesaComponent despesaComponent;
    private FornecedorComponent fornecedorComponent;



    @Autowired
    public FinanceiroCommands(DespesaComponent despesaComponent,
                              FornecedorComponent fornecedorComponent) {

        this.despesaComponent = despesaComponent;
        this.fornecedorComponent = fornecedorComponent;


    }

    @ShellMethod("Consulta Fornecedor")
    public void fornecedores() {
        ConsultaFornecedor consultaFornecedor = new ConsultaFornecedor(fornecedorComponent);
        consultaFornecedor.consulta();
    }

    @ShellMethod("Cadastro Fornecedor")
    public void addFornecedor() {
        CadastroFornecedor cadastroFornecedor = new CadastroFornecedor(fornecedorComponent);
        cadastroFornecedor.cadastrar();
    }

    @ShellMethod("Consulta Despesas")
    public void despesas(){
            ConsultaDespesa consultaDespesa = new ConsultaDespesa(despesaComponent);
            consultaDespesa.consulta();
    }

    @ShellMethod("Cadastro Despesas")
    public void addDespesa() {
        CadastroDespesa cadastroDespesa =  new CadastroDespesa(despesaComponent);
        cadastroDespesa.cadastrar();
    }
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
