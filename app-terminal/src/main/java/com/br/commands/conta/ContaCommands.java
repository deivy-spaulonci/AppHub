package com.br.commands.conta;

import com.br.business.service.ContaService;
import com.br.business.service.FormaPagamentoService;
import com.br.business.service.FornecedorService;
import com.br.business.service.TipoContaService;
import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.entity.*;
import com.br.filter.ContaFilter;
import com.br.util.Util;
import com.br.util.Validate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@ShellComponent
public class ContaCommands  extends AbstractShellComponent {
    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;
    @Setter
    @Getter
    @Autowired
    private Terminal terminal;
    private DefaultComponent defaultComponent;
    @Autowired
    private ContaService contaService;
    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private TipoContaService tipoContaService;
    @Autowired
    private FornecedorService fornecedorService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;



//    @ShellMethod("castrasdo conta")
//    public void addConta(){
//        System.out.print("\033\143");
//        //saveEditConta(new Conta());
//    }

//    public void saveEditConta(Conta conta){
//        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//
//        conta.setTipoConta(this.comboTipoConta());
//        if(!this.inputCodigoBarra(conta) ||
//                !this.inputEmissao(conta) ||
//                !this.inputVencimento(conta) ||
//                !this.inputParcelas(conta) ||
//                !this.inputValor(conta))
//            return;
//
//        if(conta.getTipoConta().getCartaoCredito() &&
//                this.defaultComponent.confirmationInput("Esta conta tem faturas de cartao. Lançar faturas", true)){
//            this.addFaturas(conta);
//        }
//
//        boolean contapaga = this.defaultComponent.confirmationInput("Conta ja esta paga", false);
////        if(contapaga){
////            var formaPagamento = this.defaultComponent.selectFormaPagamento();
////            conta.setFormaPagamento(FormaPagamento.forValues(formaPagamento));
////            if(!this.inputDataPagamento(conta))
////                return;
////        }else{
////            conta.setFormaPagamento(null);
////            conta.setDataPagamento(null);
////        };
//
//        conta.setObs(this.defaultComponent.inputoObs());
//
//        System.out.print("\033\143");
//        this.showConta(conta);
//
//        if(this.defaultComponent.confirmationInput("Salvar conta", true)){
//            if(contaService.save(conta)!=null)
//                shellHelper.printInfo("Salvo com sucesso!");
//                else
//                shellHelper.printError("Erro ao Salvar conta");
//        }
//    }





    public TipoConta comboTipoConta(){
//        List<SelectorItem<String>> tipos = new ArrayList<>();
//        tipoContaService.findTipoContas(Sort.by("nome")).forEach(tipoConta -> {
//            tipos.add(SelectorItem.of(tipoConta.getNome(), tipoConta.getId().toString()));
//        });
//        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Tipo Conta");
//        return tipoContaService.findById(new BigInteger(id)).get();
        return null;
    }

//    public void addFaturas(Conta conta){
//        List<Fatura> faturas = new ArrayList<>();
//        //shellHelper.printInfo(String.format("%-80s | %-10s | %5s | %10s", "Fornecedor", "Data", "Parcelas", "Valor"));
//        do{
//            Fatura fatura = new Fatura();
//            this.comboFornecedor(fatura);
//            if(!this.inputValorFatura(fatura) || !this.inputParcelasFatura(fatura) || !this.inputDataFatura(fatura))
//                return;
//            faturas.add(fatura);
//            System.out.print("\033\143");
//            conta.setFaturas(faturas);
//            showConta(conta);
//        }while (this.defaultComponent.confirmationInput("Adicionar outra fatura", true));
//        conta.setFaturas(faturas);
//    }


    public boolean inputEmissao(Conta conta){
        boolean valid = false;
        do{
            String emissao = this.defaultComponent.inputTextDefault("Emissao -> ", "");
            valid = !emissao.isEmpty() && Validate.isValidDate(emissao);
            if(valid)
                conta.setEmissao(Util.getData(emissao));
        } while (valid ? false :  this.defaultComponent.confirmationInput("Emissao inválida! Continuar", true));
        return valid;
    }

    public boolean inputVencimento(Conta conta){
        boolean valid = false;
        do{
            String vencimento = this.defaultComponent.inputTextDefault("Vencimento -> ", "");
            valid = !vencimento.trim().isEmpty() && Validate.isValidDate(vencimento) && vencimento.matches("[0-9]+");
            if(valid){
                conta.setVencimento(Util.getData(vencimento));
                valid = (conta.getVencimento().compareTo(conta.getEmissao()) >= 0) ;
            }
        } while (valid ? false :  this.defaultComponent.confirmationInput("Vencimento inválido! Continuar", true));
        return valid;
    }

    public boolean inputParcelas(Conta conta){
        boolean valid = false;
        do{
            String parcela = this.defaultComponent.inputTextDefault("Parcela  ->", "00");
            String totalPa = this.defaultComponent.inputTextDefault("Total Parcelas  ->", "00");
            valid = parcela.matches("[0-9]+") && totalPa.matches("[0-9]+");
            if(valid){
                conta.setParcela(Integer.parseInt(parcela));
                conta.setTotalParcela(Integer.parseInt(totalPa));
                valid = conta.getTotalParcela() >= conta.getParcela();
            }else{
                conta.setParcela(0);
                conta.setTotalParcela(0);
            }
        } while (valid ? false :  this.defaultComponent.confirmationInput("Parcelas inválida! Continuar", true));
        return valid;
    }
//
//    public boolean inputValor(Conta conta){
//        boolean valid = false;
//        do{
//            var valor = this.defaultComponent.inputValor().replaceAll(",", ".");
//            try{
//                conta.setValor(new BigDecimal(valor));
//                valid = conta.getValor().compareTo(BigDecimal.ZERO) == 1;
//            }catch(NumberFormatException e){
//                valid  = false;
//            }
//        }while (valid ? false :  this.defaultComponent.confirmationInput("Valor inválido! Continuar", true));
//        return valid;
//    }
//
//    public void comboFornecedor(Fatura fatura){
//        List<SelectorItem<String>> tipos = new ArrayList<>();
//        fornecedorService.listFornecedoresSorted(null, Sort.by("nome"))
//                .forEach(fornecedor -> {
//            tipos.add(SelectorItem.of(fornecedor.getNome(), fornecedor.getId().toString()));
//        });
//        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Fornecedor");
//        fatura.setFornecedor(fornecedorService.findById(new BigInteger(id)).get());
//    }
//
//    public boolean inputValorFatura(Fatura fatura){
//        boolean valid = false;
//        do{
//            var valor = this.defaultComponent.inputValor().replaceAll(",", ".");
//            try{
//                fatura.setValor(new BigDecimal(valor));
//                valid = fatura.getValor().compareTo(BigDecimal.ZERO) == 1;
//            }catch(NumberFormatException e){
//                valid  = false;
//            }
//        }while (valid ? false :  this.defaultComponent.confirmationInput("Valor inválido! Continuar", true));
//        return valid;
//    }


    public boolean inputDataPagamento(Conta conta){
        boolean valid = false;
        do{
            String dataPgto = this.defaultComponent.inputTextDefault("Data pagamento -> ", conta.getVencimento().toString());
            valid = !dataPgto.isEmpty() && Validate.isValidDate(dataPgto);
            if(valid)
                conta.setDataPagamento(Util.getData(dataPgto));
        } while (valid ? false :  this.defaultComponent.confirmationInput("Data pagamento inválida! Continuar", true));
        return valid;
    }
//
//    public Optional<Conta> comboConta(ContaFilter contaFilter){
//        List<SelectorItem<String>> tipos = new ArrayList<>();
//        contaService.listContaSorted(contaFilter, Sort.by("vencimento").descending())
//                .forEach(conta -> {
//                    String label = String.format("%-7s | %-60s | %-10s | %-10s | %10s | %8s | %20s | %-30s",
//                            conta.getId(),
//                            conta.getTipoConta().getNome(),
//                            conta.getVencimento(),
//                            conta.getEmissao(),
//                            conta.getValor(),
//                            (conta.getParcela()+"/"+conta.getTotalParcela()),
////                            conta.getStatus(),
//                            (conta.getObs()==null ? "" : conta.getObs()));
//                    tipos.add(SelectorItem.of(label, conta.getId().toString()));
//                });
//        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Conta");
//        return contaService.findById(new BigInteger(id));
//    }
}
