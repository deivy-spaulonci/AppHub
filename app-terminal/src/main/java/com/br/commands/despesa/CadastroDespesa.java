package com.br.commands.despesa;

import com.br.business.service.*;
import com.br.commands.DefaultComponent;
import com.br.commands.fornecedor.CadastroFornecedor;
import com.br.config.ShellHelper;
import com.br.entity.Despesa;
import com.br.entity.Fornecedor;
import com.br.util.Util;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class CadastroDespesa {

    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;
    private FornecedorService fornecedorService;
    private CidadeService cidadeService;

    public CadastroDespesa(DefaultComponent defaultComponent,
                           ShellHelper shellHelper) {
        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
    }

//    public void cadastrar(TipoDespesaService tipoDespesaService,
//                          FormaPagamentoService formaPagamentoService,
//                          DespesaService despesaService,
//                          FornecedorService fornecedorService,
//                          CidadeService cidadeService) {
//        this.fornecedorService = fornecedorService;
//        this.cidadeService = cidadeService;
//        var cont = true;
//        Despesa despesaCadastro = new Despesa();
//        do {
//            System.out.print("\033\143");
//            shellHelper.printInfo("+"+"-".repeat(100));
//            //TIPO DESPESA ----------------------------------------------------
//            despesaCadastro.setTipoDespesa(this.defaultComponent.selectTipoDespesa(tipoDespesaService.findTipoDespesas()));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Tipo Despesa",
//                    despesaCadastro.getTipoDespesa().getId()+"-"+ despesaCadastro.getTipoDespesa().getNome()));
//
//            //FORNECEDOR ----------------------------------------------------
//            CadastroFornecedor cadastroFornecedor = new CadastroFornecedor(this.defaultComponent, this.shellHelper);
//            Fornecedor fornecedor = cadastroFornecedor.cadastro(fornecedorService, cidadeService);
//            if(fornecedor==null)
//                return;
//            despesaCadastro.setFornecedor(fornecedor);
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Fornecedor", despesaCadastro.getFornecedor().getNome()));
//
//            //DATA ----------------------------------------------------
//            String datatxt = defaultComponent.inData("Data Pagamento");
//            despesaCadastro.setDataPagamento(Util.getData(datatxt));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Data pagamento", Util.toDatePtBr(despesaCadastro.getDataPagamento())));
//
//            //VALOR ----------------------------------------------------
//            String valortxt = defaultComponent.inValor("Valor");
//            despesaCadastro.setValor(new BigDecimal(valortxt));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Valor", Util.toCurrencyPtBr(despesaCadastro.getValor())));
//
//            //FORMA PAGAMENTO ----------------------------------------------------
//            despesaCadastro.setFormaPagamento(this.defaultComponent.selectFormaPagamento(formaPagamentoService.findFormasPagamento()));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Forma Pagamento",
//                    despesaCadastro.getFormaPagamento().getId()+" - "+despesaCadastro.getFormaPagamento().getNome()));
//
//            //OBSERVAÇÃO ----------------------------------------------------
//            despesaCadastro.setObs(this.defaultComponent.inputoObs());
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Observação",despesaCadastro.getObs()));
//
//            shellHelper.printInfo("+"+"-".repeat(100));
//
//            cont = this.defaultComponent.confirmationInput("Salvar Despesa", true);
//            if(cont==true)
//                despesaService.save(despesaCadastro);
//            else
//                despesaCadastro = new Despesa();
//            cont = this.defaultComponent.confirmationInput("Incluir outra Despesa", true);
//        }while(cont);
//    }

}
