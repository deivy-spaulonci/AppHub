package com.br.commands.conta;

import com.br.business.service.ContaService;
import com.br.business.service.FornecedorService;
import com.br.business.service.TipoContaService;
import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.entity.Conta;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class CadastroContas {
    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;
    private TipoContaService tipoContaService;
    private ContaService contaService;
    private FornecedorService fornecedorService;

    public CadastroContas(DefaultComponent defaultComponent, ShellHelper shellHelper) {
        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
    }

//    public void cadastrar(ContaService contaService,
//                          TipoContaService tipoContaService,
//                          FormaPagamentoService formaPagamentoService,
//                          FornecedorService fornecedorService) {
//        this.tipoContaService = tipoContaService;
//        this.contaService = contaService;
//        this.fornecedorService = fornecedorService;
//
//        System.out.print("\033\143");
//        shellHelper.printInfo("+"+"-".repeat(110));
//
//        //CODIGO DE BRRAS ----------------------------------------------------
//        Conta conta = new Conta();
//        if(!this.inputCodigoBarra(conta))
//            return;
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Código Barras", conta.getCodigoBarra()));
//
//        //TIPO CONTA ----------------------------------------------------
//        conta.setTipoConta(this.defaultComponent.selectTipoConta(tipoContaService.findTipoContas()));
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Tipo Conta", conta.getTipoConta().getNome()));
//
//        //EMISSAO  ----------------------------------------------------
//        conta.setEmissao(Util.getData(this.defaultComponent.inData("Emissão")));
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Emissão", Util.toDatePtBr(conta.getEmissao())));
//
//        //VENCIMETNO  ----------------------------------------------------
//        conta.setVencimento(Util.getData(this.defaultComponent.inData("Vencimento")));
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Vencimento", Util.toDatePtBr(conta.getVencimento())));
//
//        //PARCELAS  ----------------------------------------------------
//        if(!inputParcela(conta))
//            return;
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Parcelas ", conta.getParcela() +"/"+ conta.getTotalParcela()));
//
//        //VALOR   ----------------------------------------------------
//        conta.setValor(new BigDecimal(this.defaultComponent.inValor("Valor")));
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Valor ", Util.toCurrencyPtBr(conta.getValor())));
//
//        //OBSERVAÇÃO ----------------------------------------------------
//        conta.setObs(this.defaultComponent.inputoObs());
//        shellHelper.printInfo("| %-16s : %-25s".formatted("Observação",conta.getObs()));
//
//        if(this.defaultComponent.confirmationInput("Conta ja paga", false)){
//            //FORMA PAGAMENTO ----------------------------------------------------
//            conta.setFormaPagamento(this.defaultComponent.selectFormaPagamento(formaPagamentoService.findFormasPagamento()));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Forma Pagamento",conta.getFormaPagamento().getNome()));
//
//            //DATA PAGAMENTO ----------------------------------------------------
//            conta.setDataPagamento(Util.getData(this.defaultComponent.inData("Data Pagamento")));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Data Pagamento", Util.toDatePtBr(conta.getDataPagamento())));
//
//            //VALOR PAGO/DESCONTO/MULTA ----------------------------------------------------
//            inputCalcValuePgto(conta);
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Desconto ", Util.toCurrencyPtBr(conta.getDesconto())));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Multa ", Util.toCurrencyPtBr(conta.getMulta())));
//            shellHelper.printInfo("| %-16s : %-25s".formatted("Total Pago",
//                    Util.toCurrencyPtBr(conta.getValor().subtract(conta.getDesconto()).add(conta.getMulta()))));
//        }
//
//
//        System.out.print("\033\143");
//        ConsultaConta consultaConta = new ConsultaConta(this.defaultComponent, this.shellHelper);
//        consultaConta.showConta(conta);
//
//        if(this.defaultComponent.confirmationInput("Salvar conta", true)){
//            contaService.save(conta);
//        }
//    }

//    public void inputCalcValuePgto(Conta conta){
//        BigDecimal valorPago = new BigDecimal(this.defaultComponent.inputValor("Valor Pago"));
//        int comp = valorPago.compareTo(conta.getValor());
//        conta.setDesconto(BigDecimal.ZERO);
//        conta.setMulta(BigDecimal.ZERO);
//        if(comp < 0){
//            conta.setDesconto(conta.getValor().subtract(valorPago));
//        }else if(comp > 0)
//            conta.setMulta(valorPago.subtract(conta.getValor()));
//    }

//    public boolean inputCodigoBarra(Conta conta){
//        boolean valid = false;
//        do{
//            conta.setCodigoBarra(this.defaultComponent.inputTextDefault(" Código Barras ->", "0".repeat(48)));
//            valid = conta.getCodigoBarra().matches("[0-9]+") && conta.getCodigoBarra().length()==47;
//        }while (!valid && this.defaultComponent.confirmationInput("Código Barra inválido! Continuar", true));
//        return valid;
//    }
//
//    public boolean inputParcela(Conta conta){
//        boolean valid = false;
//        do{
//            String parcela = "0";
//            String total = "0";
//            do{
//                parcela = this.defaultComponent.inputTextDefault(" Parcela ->", "0");
//                valid = parcela.matches("[0-9]+") && parcela.length()<=3;
//            }while(!valid && this.defaultComponent.confirmationInput("Parcela inválida! Continuar", true));
//            conta.setParcela(Integer.valueOf(parcela));
//
//            do{
//                total = this.defaultComponent.inputTextDefault(" Total ->", "0");
//                valid = total.matches("[0-9]+") && total.length()<=3;
//            }while(!valid && this.defaultComponent.confirmationInput("Total inválido! Continuar", true));
//            conta.setTotalParcela(Integer.valueOf(total));
//
//            valid = (conta.getParcela().compareTo(conta.getTotalParcela())<=0);
//
//        }while (!valid && this.defaultComponent.confirmationInput("Parcela maior que seu total! Continuar", true));
//        return valid;
//    }


}
