package com.br.commands;

import com.br.business.service.ContaService;
import com.br.business.service.FornecedorService;
import com.br.business.service.TipoContaService;
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

    @ShellMethod("status contas")
    public void statusConta(){
        shellHelper.printInfo("-".repeat(50));
        ContaFilter contaFilter = ContaFilter.builder().build();
        shellHelper.printInfo(String.format("> Registros: %-10s", contaService.getCountConta()));
        shellHelper.printInfo(String.format("> " + "Total: %-10s", Util.toCurrencyPtBr(contaService.getSumConta(contaFilter))));
        shellHelper.printInfo("-".repeat(50));
    }

    public Page<Conta> loadTableContas(int order, int size, int numberPg, String campoSort, ContaFilter contaFilter){
        Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(numberPg-1, size, Sort.by(direction, campoSort));
        Page<Conta> pg = contaService.listContaPaged(contaFilter, pageable);
        String header = String.format("%-7s | %-60s | %-10s | %-10s | %15s | %8s | %20s | %-30s", "Id", "Conta", "Emissão", "Vencimento", "Valor", "Parcelas", "Status", "Obs");
        System.out.print("\033\143");
        shellHelper.printInfo("-".repeat(182));
        shellHelper.printInfo(header);
        shellHelper.printInfo("-".repeat(182));
        if(pg.hasContent()){
            pg.getContent().forEach(conta -> {
                String label = String.format("%-7s | %-60s | %-10s | %-10s | %15s | %8s | %20s | %-30s",
                        conta.getId(),
                        conta.getTipoConta().getNome(),
                        Util.toDatePtBr(conta.getEmissao()),
                        Util.toDatePtBr(conta.getVencimento()),
                        Util.toCurrencyPtBr(conta.getValor()),
                        (conta.getParcela()+"/"+conta.getTotalParcela()),
                        conta.getStatus(),
                        (conta.getObs()==null ? "" : conta.getObs()));
                switch (conta.getIntStatus()){
                    case 0 : shellHelper.printWarning(label); break;
                    case 1 : shellHelper.printInfo(label); break;
                    case -1 : shellHelper.printError(label); break;
                    default : shellHelper.printSuccess(label); break;
                }
            });
        }else
            shellHelper.printInfo("nenhum conta encontrada...");
        shellHelper.printInfo("-".repeat(182));
        BigDecimal total = contaService.getSumConta(contaFilter);
        shellHelper.printInfo("");
        shellHelper.printInfo(String.format(" pagina: %-10s paginas: %-10s tamanho: %-10s  registros: %-10s  total: %-10s", numberPg, pg.getTotalPages(), size, pg.getTotalElements(), Util.toCurrencyPtBr(total)));

        shellHelper.printInfo("Filtros:");
        if(contaFilter.getId()!=null) shellHelper.printInfo("	-> id = " + contaFilter.getId());
        if(contaFilter.getTipoConta()!=null) shellHelper.printInfo("	-> Tipo despesa = " + contaFilter.getTipoConta().getNome());
        if(contaFilter.getVencimentoInicial()!=null) shellHelper.printInfo("	-> Vencimetno inicio = " + contaFilter.getVencimentoInicial());
        if(contaFilter.getVencimentoFinal()!=null) shellHelper.printInfo("	-> Vencimento termino = " + contaFilter.getVencimentoFinal());
        shellHelper.printInfo("");
        shellHelper.printInfo("paginação > [exit]   [pag xx]   [order 'xx']  [size]");
        shellHelper.printInfo("filtros   > [id xx]  [tipo]     [vencimento 00/00/0000 00/00/0000]  [status]  [clear]");
        shellHelper.printInfo("comandos  > [clear]  [pagar xx] [editar xx]  [detalhe xx]");
        shellHelper.printInfo("");

        return pg;
    }

    @ShellMethod("consulta contas")
    public void findContas(){
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
        boolean contEdtConta = true;
        ContaFilter contaFilter = ContaFilter.builder().build();
        int size = 20;
        int numberPage = 1; //numero da pag
        String campoOrder = "vencimento";
        int order = 1;
        do {
            Page<Conta> pg = this.loadTableContas(order, size, numberPage, campoOrder, contaFilter);

            String resposta = this.defaultComponent.inputTextDefault(" ->", "");
            if(resposta.equals("exit")){
                contEdtConta = false;
            }else {
                String[] resparr = resposta.split(" ");
                switch (resparr[0]) {
                    case "size":
                        size = this.defaultComponent.selectPageDefault();
                        break;
                    case "pag": {
                        if (resparr[1].matches("[0-9]+")) {
                            Integer retInt = Integer.parseInt(resparr[1]);
                            if (retInt >= 1 && retInt <= pg.getTotalPages()) {
                                numberPage = pg.getContent().size() < size ? 1 : retInt;
                            } else
                                shellHelper.printError(String.format("valor digitado tem que ser entre 1 e %d", pg.getTotalPages()));
                        } else
                            shellHelper.printError("valor digitado nao corresponde a um numero");
                        break;
                    }
                    case "order":{
                        List<SelectorItem<String>> ltOpc = new ArrayList<>(){
                            {
                                add(SelectorItem.of("Id", "id"));
                                add(SelectorItem.of("Tipo Conta", "tipoConta"));
                                add(SelectorItem.of("Vencimento", "vencimento"));
                                add(SelectorItem.of("Emissão", "emissao"));
                            }
                        };
                        campoOrder = this.defaultComponent.selectDefault(ltOpc, "Selecionar Campo");

                        List<SelectorItem<String>> opcOrder = new ArrayList<>();
                        opcOrder.add(SelectorItem.of("Ascesdente", "0"));
                        opcOrder.add(SelectorItem.of("Descendente", "1"));
                        order = Integer.valueOf(this.defaultComponent.selectDefault(opcOrder, "Ordem"));

                        break;
                    }
                    case "id": {
                        //String id = re//this.defaultComponent.inputTextDefault(" id ->", "");
                        if(resparr[1] != null && resparr[1].matches("[0-9]+")) {
                            contaFilter.setId(Long.valueOf(resparr[1]));
                            numberPage = 1;
                        }else
                            shellHelper.printError("valor digitado nao corresponde a um numero");
                        break;
                    }
                    case "tipo": {
                        contaFilter.setTipoConta(this.comboTipoConta());
                        numberPage = 1;
                        break;
                    }
//                    case "vencimento":{
//                        if(resparr[1]!=null && Validate.isValidDate(resparr[1])) {
//                            contaFilter.setVencimentoInicial(Util.getData(resparr[1]));
//                            numberPage = 1;
//                            if(resparr.length==3 && Validate.isValidDate(resparr[2]))
//                                contaFilter.setVencimentoFinal(Util.getData(resparr[2]));
//                            else
//                                shellHelper.printError("data inválida!");
//                        }else
//                            shellHelper.printError("data inválida!");
//                        break;
//                    }
//                    case "status":{
//                        List<SelectorItem<String>> ltStatus = new ArrayList<>();
//                        ltStatus.add(SelectorItem.of("Em Aberto", "ABERTO"));
//                        ltStatus.add(SelectorItem.of("Hoje", "HOJE"));
//                        ltStatus.add(SelectorItem.of("Em Atraso", "ATRASADO"));
//                        ltStatus.add(SelectorItem.of("Pago", "PAGO"));
//                        var status = this.defaultComponent.selectDefault(ltStatus, "Selecionar Status");
//                        contaFilter.setContaStatus(ContaStatus.forValues(status));
//                        numberPage = 1;
//                        break;
//                    }
//                    case "pagar" :{
//                        if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                            Optional<Conta> optionalConta = this.contaService.findById(new BigInteger(resparr[1].toString()));
//                            if(optionalConta.isEmpty())
//                                shellHelper.printError("Id da conta não econtrado !");
//                            optionalConta.ifPresent(this::setContaPaga);
//
//                        }else
//                            shellHelper.printError("valor digitado nao corresponde a um numero");
//                        this.defaultComponent.confirmationInput("enter para continuar", true);
//                        break;
//                    }
//                    case "editar" :{
//                        if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                            Optional<Conta> optionalConta = this.contaService.findById(new BigInteger(resparr[1].toString()));
//                            optionalConta.ifPresent(this::saveEditConta);
//                            shellHelper.printError("Id da conta não econtrado !");
//                        }else
//                            shellHelper.printError("valor digitado nao corresponde a um numero");
//                        this.defaultComponent.confirmationInput("enter para continuar", true);
//                        break;
//                    }
//                    case "detalhe" :{
//                        if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                            Optional<Conta> optionalConta = this.contaService.findById(new BigInteger(resparr[1].toString()));
//                            System.out.print("\033\143");
//                            optionalConta.ifPresent(this::showConta);
//                        }else
//                            shellHelper.printError("valor digitado nao corresponde a um numero");
//                        this.defaultComponent.confirmationInput("enter para continuar", true);
//                        break;
//                    }
//                    case "clear": {
//                        contaFilter = ContaFilter.builder().build();
//                        break;
//                    }
                    default: numberPage = pg.getContent().size() < size ? 1 : numberPage+1;
                }
            }
        }while (contEdtConta);
    }

    @ShellMethod("castrasdo conta")
    public void addConta(){
        System.out.print("\033\143");
        saveEditConta(new Conta());
    }

    public void saveEditConta(Conta conta){
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());

        conta.setTipoConta(this.comboTipoConta());
        if(!this.inputCodigoBarra(conta) ||
                !this.inputEmissao(conta) ||
                !this.inputVencimento(conta) ||
                !this.inputParcelas(conta) ||
                !this.inputValor(conta))
            return;

        if(conta.getTipoConta().getCartaoCredito() &&
                this.defaultComponent.confirmationInput("Esta conta tem faturas de cartao. Lançar faturas", true)){
            this.addFaturas(conta);
        }

        boolean contapaga = this.defaultComponent.confirmationInput("Conta ja esta paga", false);
//        if(contapaga){
//            var formaPagamento = this.defaultComponent.selectFormaPagamento();
//            conta.setFormaPagamento(FormaPagamento.forValues(formaPagamento));
//            if(!this.inputDataPagamento(conta))
//                return;
//        }else{
//            conta.setFormaPagamento(null);
//            conta.setDataPagamento(null);
//        };

        conta.setObs(this.defaultComponent.inputoObs());

        System.out.print("\033\143");
        this.showConta(conta);

        if(this.defaultComponent.confirmationInput("Salvar conta", true)){
            if(contaService.save(conta)!=null)
                shellHelper.printInfo("Salvo com sucesso!");
                else
                shellHelper.printError("Erro ao Salvar conta");
        }
    }

    public void setContaPaga(Conta conta){
        System.out.print("\033\143");
        this.showConta(conta);
        if(this.defaultComponent.confirmationInput("Setar conta como Paga", true)){
            this.inputDataPagamento(conta);
            this.inputValor(conta);
//            conta.setFormaPagamento(FormaPagamento.forValues(this.defaultComponent.selectFormaPagamento()));
            contaService.save(conta);
        }
    }

    public void showConta(Conta conta){
        shellHelper.printInfo(String.format(" Tipo Conta       : %10s", conta.getTipoConta().getNome()));
        shellHelper.printInfo(String.format(" Codoigo de barras: %10s", conta.getCodigoBarra()));
        shellHelper.printInfo(String.format(" Emissão          : %10s", Util.toDatePtBr(conta.getEmissao())));
        shellHelper.printInfo(String.format(" Vencimento       : %10s", Util.toDatePtBr(conta.getVencimento())));
        shellHelper.printInfo(String.format(" Parcelas         : %s/%s", conta.getParcela(), conta.getTotalParcela()));
        shellHelper.printInfo(String.format(" Valor            : %10s", Util.toCurrencyPtBr(conta.getValor())));

        if(conta.getFormaPagamento()!=null && conta.getDataPagamento()!=null){
            shellHelper.printInfo("");
            shellHelper.printInfo("Pagamento: "+"_".repeat(130));
            shellHelper.printInfo(String.format("Forma Pagamento  : %10s", conta.getFormaPagamento().getNome()));
            shellHelper.printInfo(String.format("Data  Pagamento  : %10s", Util.toDatePtBr(conta.getDataPagamento())));
        }

        if(conta.getTipoConta().getCartaoCredito() && !conta.getFaturas().isEmpty()){
            shellHelper.printInfo("");
            shellHelper.printInfo("Faturas: "+"_".repeat(130));
            conta.getFaturas().forEach(fatura -> {
                String label = String.format("%-80s | %-10s | %5s | %10s",
                        fatura.getFornecedor().getNome(),
                        Util.toDatePtBr(fatura.getDataPagamento()),
                        fatura.getParcela()+"/"+fatura.getTotalParcela(),
                        fatura.getValor());
                shellHelper.printWarning(label);
            });
        }
    }

    public TipoConta comboTipoConta(){
        List<SelectorItem<String>> tipos = new ArrayList<>();
        tipoContaService.findTipoContas(Sort.by("nome")).forEach(tipoConta -> {
            tipos.add(SelectorItem.of(tipoConta.getNome(), tipoConta.getId().toString()));
        });
        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Tipo Conta");
        return tipoContaService.findById(new BigInteger(id)).get();
    }

    public void addFaturas(Conta conta){
        List<Fatura> faturas = new ArrayList<>();
        //shellHelper.printInfo(String.format("%-80s | %-10s | %5s | %10s", "Fornecedor", "Data", "Parcelas", "Valor"));
        do{
            Fatura fatura = new Fatura();
            this.comboFornecedor(fatura);
            if(!this.inputValorFatura(fatura) || !this.inputParcelasFatura(fatura) || !this.inputDataFatura(fatura))
                return;
            faturas.add(fatura);
            System.out.print("\033\143");
            conta.setFaturas(faturas);
            showConta(conta);
        }while (this.defaultComponent.confirmationInput("Adicionar outra fatura", true));
        conta.setFaturas(faturas);
    }

    public boolean inputCodigoBarra(Conta conta){
        boolean valid = false;
        do{
            conta.setCodigoBarra(this.defaultComponent.inputTextDefault(" Código Barras ->", "0".repeat(48)));
            valid = conta.getCodigoBarra().matches("[0-9]+");
        }while ( valid ? false : this.defaultComponent.confirmationInput("Código Barra inválido! Continuar", true));
        return valid;
    }

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

    public boolean inputValor(Conta conta){
        boolean valid = false;
        do{
            var valor = this.defaultComponent.inputValor().replaceAll(",", ".");
            try{
                conta.setValor(new BigDecimal(valor));
                valid = conta.getValor().compareTo(BigDecimal.ZERO) == 1;
            }catch(NumberFormatException e){
                valid  = false;
            }
        }while (valid ? false :  this.defaultComponent.confirmationInput("Valor inválido! Continuar", true));
        return valid;
    }

    public void comboFornecedor(Fatura fatura){
        List<SelectorItem<String>> tipos = new ArrayList<>();
        fornecedorService.listFornecedoresSorted(null, Sort.by("nome"))
                .forEach(fornecedor -> {
            tipos.add(SelectorItem.of(fornecedor.getNome(), fornecedor.getId().toString()));
        });
        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Fornecedor");
        fatura.setFornecedor(fornecedorService.findById(new BigInteger(id)).get());
    }

    public boolean inputValorFatura(Fatura fatura){
        boolean valid = false;
        do{
            var valor = this.defaultComponent.inputValor().replaceAll(",", ".");
            try{
                fatura.setValor(new BigDecimal(valor));
                valid = fatura.getValor().compareTo(BigDecimal.ZERO) == 1;
            }catch(NumberFormatException e){
                valid  = false;
            }
        }while (valid ? false :  this.defaultComponent.confirmationInput("Valor inválido! Continuar", true));
        return valid;
    }

    public boolean inputParcelasFatura(Fatura fatura){
        boolean valid = false;
        do{
            String parcela = this.defaultComponent.inputTextDefault("Parcela  ->", "00");
            String totalPa = this.defaultComponent.inputTextDefault("Total Parcelas  ->", "00");
            valid = parcela.matches("[0-9]+") && totalPa.matches("[0-9]+");
            if(valid){
                fatura.setParcela(Integer.parseInt(parcela));
                fatura.setTotalParcela(Integer.parseInt(totalPa));
                valid = fatura.getTotalParcela() >= fatura.getParcela();
            }else{
                fatura.setParcela(0);
                fatura.setTotalParcela(0);
            }
        } while (valid ? false :  this.defaultComponent.confirmationInput("Parcelas inválida! Continuar", true));
        return valid;
    }

    public boolean inputDataFatura(Fatura fatura){
        boolean valid = false;
        do{
            String vencimento = this.defaultComponent.inputTextDefault("Data -> ", "");
            valid = !vencimento.trim().isEmpty() && Validate.isValidDate(vencimento) && vencimento.matches("[0-9]+");
            if(valid)
                fatura.setDataPagamento(Util.getData(vencimento));

        } while (valid ? false :  this.defaultComponent.confirmationInput("Data inválido! Continuar", true));
        return valid;
    }

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

    public Optional<Conta> comboConta(ContaFilter contaFilter){
        List<SelectorItem<String>> tipos = new ArrayList<>();
        contaService.listContaSorted(contaFilter, Sort.by("vencimento").descending())
                .forEach(conta -> {
                    String label = String.format("%-7s | %-60s | %-10s | %-10s | %10s | %8s | %20s | %-30s",
                            conta.getId(),
                            conta.getTipoConta().getNome(),
                            conta.getVencimento(),
                            conta.getEmissao(),
                            conta.getValor(),
                            (conta.getParcela()+"/"+conta.getTotalParcela()),
                            conta.getStatus(),
                            (conta.getObs()==null ? "" : conta.getObs()));
                    tipos.add(SelectorItem.of(label, conta.getId().toString()));
                });
        String id = this.defaultComponent.selectDefault(tipos, "Selecionar Conta");
        return contaService.findById(new BigInteger(id));
    }
}
