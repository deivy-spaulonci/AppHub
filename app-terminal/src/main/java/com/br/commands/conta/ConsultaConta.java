package com.br.commands.conta;

import com.br.business.service.ContaService;
import com.br.business.service.FormaPagamentoService;
import com.br.business.service.TipoContaService;
import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.entity.Conta;
import com.br.entity.ContaStatus;
import com.br.entity.FormaPagamento;
import com.br.filter.ContaFilter;
import com.br.util.Util;
import com.br.util.Validate;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ConsultaConta {

    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;

    public ConsultaConta(DefaultComponent defaultComponent, ShellHelper shellHelper) {
        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
    }

//    public void consulta(ContaService contaService, TipoContaService tipoContaService, FormaPagamentoService formaPagamentoService) {
//
//        ContaFilter contaFilter = ContaFilter.builder().build();
//        int size = 30;
//        int numberPage = 1; //numero da pag
//        String campoOrder = "vencimento";
//        int order = 1;
//        String opcao;
//        do {
//            Page<Conta> pg = this.loadTableContas(order, size, numberPage, campoOrder, contaFilter, contaService);
//
//            opcao = this.defaultComponent.inputTextDefault(" ->", "");
//            String[] resparr = opcao.split(" ");
//            switch (resparr[0]) {
//                case "size":
//                    size = this.defaultComponent.selectPageDefault();
//                    break;
//                case "pg": {
//                    if (resparr[1].matches("[0-9]+")) {
//                        Integer retInt = Integer.parseInt(resparr[1]);
//                        if (retInt >= 1 && retInt <= pg.getTotalPages()) {
//                            numberPage = pg.getContent().size() < size ? 1 : retInt;
//                        } else
//                            shellHelper.printError(String.format("valor digitado tem que ser entre 1 e %d", pg.getTotalPages()));
//                    } else
//                        shellHelper.printError("valor digitado nao corresponde a um numero");
//                    break;
//                }
//                case "order":{
//                    List<SelectorItem<String>> ltOpc = new ArrayList<>(){
//                        {
//                            add(SelectorItem.of("Id", "id"));
//                            add(SelectorItem.of("Tipo Conta", "tipoConta"));
//                            add(SelectorItem.of("Vencimento", "vencimento"));
//                            add(SelectorItem.of("Emissão", "emissao"));
//                        }
//                    };
//                    campoOrder = this.defaultComponent.selectDefault(ltOpc, "Selecionar Campo");
//
//                    List<SelectorItem<String>> opcOrder = new ArrayList<>();
//                    opcOrder.add(SelectorItem.of("Ascesdente", "0"));
//                    opcOrder.add(SelectorItem.of("Descendente", "1"));
//                    order = Integer.valueOf(this.defaultComponent.selectDefault(opcOrder, "Ordem"));
//
//                    break;
//                }
//                case "id": {
//                    //String id = re//this.defaultComponent.inputTextDefault(" id ->", "");
//                    if(resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                        contaFilter.setId(Long.valueOf(resparr[1]));
//                        numberPage = 1;
//                    }else
//                        shellHelper.printError("valor digitado nao corresponde a um numero");
//                    break;
//                }
//                case "tipo": {
//                    contaFilter.setTipoConta(this.defaultComponent.selectTipoConta(tipoContaService.findTipoContas()));
//                    numberPage = 1;
//                    break;
//                }
//                case "vencimento":{
//                    if(resparr[1]!=null && Validate.isValidDate(resparr[1])) {
//                        contaFilter.setVencimentoInicial(Util.getData(resparr[1]));
//                        numberPage = 1;
//                        if(resparr.length==3 && Validate.isValidDate(resparr[2]))
//                            contaFilter.setVencimentoFinal(Util.getData(resparr[2]));
//                        else
//                            shellHelper.printError("data inválida!");
//                    }else
//                        shellHelper.printError("data inválida!");
//                    break;
//                }
//                case "status":{
//                    List<SelectorItem<String>> ltStatus = new ArrayList<>();
//                    ltStatus.add(SelectorItem.of("Em Aberto", "ABERTO"));
//                    ltStatus.add(SelectorItem.of("Hoje", "HOJE"));
//                    ltStatus.add(SelectorItem.of("Em Atraso", "ATRASADO"));
//                    ltStatus.add(SelectorItem.of("Pago", "PAGO"));
//                    var status = this.defaultComponent.selectDefault(ltStatus, "Selecionar Status");
//                    contaFilter.setContaStatus(ContaStatus.forValues(status));
//                    numberPage = 1;
//                    break;
//                }
//                case "pagar" :{
//                    if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                        Optional<Conta> optionalConta = contaService.findById(new BigInteger(resparr[1].toString()));
//                        if(optionalConta.isEmpty())
//                            shellHelper.printError("Id da conta não econtrado !");
//                        else{
//                            Conta conta = setContaPaga(optionalConta.get(), formaPagamentoService.findFormasPagamento());
//                            contaService.save(conta);
//                        }
//                    }else
//                        shellHelper.printError("valor digitado nao corresponde a um numero");
//                    this.defaultComponent.confirmationInput("enter para continuar", true);
//                    break;
//                }
////                    case "editar" :{
////                        if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
////                            Optional<Conta> optionalConta = this.contaService.findById(new BigInteger(resparr[1].toString()));
////                            optionalConta.ifPresent(this::saveEditConta);
////                            shellHelper.printError("Id da conta não econtrado !");
////                        }else
////                            shellHelper.printError("valor digitado nao corresponde a um numero");
////                        this.defaultComponent.confirmationInput("enter para continuar", true);
////                        break;
////                    }
//                case "v" :{
//                    if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                        Optional<Conta> optionalConta = contaService.findById(new BigInteger(resparr[1].toString()));
//                        System.out.print("\033\143");
//                        optionalConta.ifPresent(this::showConta);
//                    }else
//                        shellHelper.printError("valor digitado nao corresponde a um numero");
//                    this.defaultComponent.confirmationInput("enter para continuar", true);
//                    break;
//                }
//                case "clear": {
//                    contaFilter = null;
//                    break;
//                }
//                default: numberPage = pg.getContent().size() < size ? 1 : numberPage+1;
//            }
//
//        }while (!opcao.equals("exit"));
//    }
//
//    public Page<Conta> loadTableContas(int order, int size, int numberPg, String campoSort, ContaFilter contaFilter, ContaService contaService) {
//        Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
//        Pageable pageable = PageRequest.of(numberPg-1, size, Sort.by(direction, campoSort));
//        //AJUSTAR
//        Page<Conta> pg = null; //contaService.listContaPaged(contaFilter, pageable);
//
//        String header = String.format("| %-7s | %-60s | %-10s | %-10s | %15s | %8s | %20s | %-30s", "Id", "Conta", "Emissão", "Vencimento", "Valor", "Parcelas", "Status", "Obs");
//        System.out.print("\033\143");
//        shellHelper.printInfo("-".repeat(182));
//        shellHelper.printInfo(header);
//        shellHelper.printInfo("-".repeat(182));
//        if(pg.hasContent()){
//            pg.getContent().forEach(conta -> {
//                String label = String.format("| %-7s | %-60s | %-10s | %-10s | %15s | %8s | %-20s | %-30s",
//                        conta.getId(),
//                        conta.getTipoConta().getNome(),
//                        Util.toDatePtBr(conta.getEmissao()),
//                        Util.toDatePtBr(conta.getVencimento()),
//                        Util.toCurrencyPtBr(conta.getValor()),
//                        (conta.getParcela()+"/"+conta.getTotalParcela()),
//                        getLabelStatus(conta),
//                        (conta.getObs()==null ? "" : conta.getObs()));
//                switch (getIntStatus(conta)){
//                    case 0 : shellHelper.printWarning(label); break;
//                    case 1 : shellHelper.printInfo(label); break;
//                    case -1 : shellHelper.printError(label); break;
//                    default : shellHelper.printSuccess(label); break;
//                }
//            });
//        }else
//            shellHelper.printInfo("nenhum conta encontrada...");
//        shellHelper.printInfo("-".repeat(182));
//        BigDecimal total = contaService.getSumConta(contaFilter);
//        shellHelper.printInfo("");
//        shellHelper.printInfo(String.format(" Pagina: %-10s Paginas: %-10s Tamanho: %-10s  Registros: %-10s  Total: %-10s", numberPg, pg.getTotalPages(), size, pg.getTotalElements(), Util.toCurrencyPtBr(total)));
//        shellHelper.printInfo("");
//        shellHelper.printInfo("Filtros:");
//        if(contaFilter != null){
//            if(contaFilter.getId()!=null) shellHelper.printInfo("	-> id = " + contaFilter.getId());
//            if(contaFilter.getTipoConta()!=null) shellHelper.printInfo("	-> Tipo despesa = " + contaFilter.getTipoConta().getNome());
//            if(contaFilter.getVencimentoInicial()!=null) shellHelper.printInfo("	-> Vencimetno inicio = " + contaFilter.getVencimentoInicial());
//            if(contaFilter.getVencimentoFinal()!=null) shellHelper.printInfo("	-> Vencimento termino = " + contaFilter.getVencimentoFinal());
//        }else
//            contaFilter = ContaFilter.builder().build();
//
//        shellHelper.printInfo("");
//        shellHelper.printInfo("Paginação > [exit ]  [pag xx  ] [order 'xx'] [size]");
//        shellHelper.printInfo("Filtros   > [id xx]  [tipo    ] [status    ] [vencimento 00/00/0000 00/00/0000]  ");
//        shellHelper.printInfo("Comandos  > [clear]  [pagar xx] [editar xx ] [v xxx (visualizar)]");
//        shellHelper.printInfo("");
//
//        return pg;
//    }
//
//    public Conta setContaPaga(Conta conta, List<FormaPagamento> formas){
//        System.out.print("\033\143");
//        this.showConta(conta);
//        if(this.defaultComponent.confirmationInput("Setar conta como Paga", true)){
//            conta.setDataPagamento(Util.getData(this.defaultComponent.inData("Data Pagamento")));
//            conta.setValor(new BigDecimal(this.defaultComponent.inValor("Valor Pagamento")));
//            conta.setFormaPagamento(this.defaultComponent.selectFormaPagamento(formas));
//        }
//        return conta;
//    }
//
//    public void showConta(Conta conta){
//        shellHelper.printInfo("+"+"-".repeat(115));
//        shellHelper.printInfo(String.format("| Tipo Conta       : %10s", conta.getTipoConta().getNome()));
//        shellHelper.printInfo(String.format("| Código de barras : %10s", conta.getCodigoBarra()));
//        shellHelper.printInfo(String.format("| Emissão          : %10s", Util.toDatePtBr(conta.getEmissao())));
//        shellHelper.printInfo(String.format("| Vencimento       : %10s", Util.toDatePtBr(conta.getVencimento())));
//        shellHelper.printInfo(String.format("| Parcelas         : %s/%s", conta.getParcela(), conta.getTotalParcela()));
//        shellHelper.printInfo(String.format("| Valor            : %10s", Util.toCurrencyPtBr(conta.getValor())));
//
//        String legenda = "| " + String.format("%c",'\u2592').repeat(51) +
//                String.format(" %-10s", getLabelStatus(conta).toUpperCase()) +
//                String.format("%c",'\u2592').repeat(51);
//
//        switch (getIntStatus(conta)){
//            case 0 : shellHelper.printWarning(legenda); break;
//            case 1 : shellHelper.printInfo(legenda); break;
//            case -1 : shellHelper.printError(legenda); break;
//            default : shellHelper.printSuccess(legenda); break;
//        }
//
//        if(conta.getFormaPagamento()!=null && conta.getDataPagamento()!=null){
//            shellHelper.printInfo("|");
//            shellHelper.printInfo("| Pagamento: "+"-".repeat(103));
//            shellHelper.printInfo(String.format("| Forma Pgto  : %10s", conta.getFormaPagamento().getNome()));
//            shellHelper.printInfo(String.format("| Data  Pgto  : %10s", Util.toDatePtBr(conta.getDataPagamento())));
//            shellHelper.printInfo(String.format("| Valor Pgto  : %10s", Util.toCurrencyPtBr((conta.getValor().add(conta.getMulta())).subtract(conta.getDesconto()))));
//        }
//        shellHelper.printInfo("+"+"-".repeat(115));
//    }
//
//    public int getIntStatus(Conta conta) {
//        if(conta.getVencimento()!=null && conta.getDataPagamento()==null){
//            if(conta.getVencimento().isAfter(LocalDate.now()))
//                return 1;
//            if(conta.getVencimento().isEqual(LocalDate.now()))
//                return 0;
//            if(conta.getVencimento().isBefore(LocalDate.now()))
//                return -1;
//        }
//        return 2;
//    }
//
//    public String getLabelStatus(Conta conta) {
//        String label = "";
//        switch (getIntStatus(conta)){
//            case 0 : label = "Vencimento hoje"; break;
//            case 1 : label = "Em aberto"; break;
//            case -1 : label = "Em atraso"; break;
//            default : label = "Pago"; break;
//        }
//        return label;
//    }
}
