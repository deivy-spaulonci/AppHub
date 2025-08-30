package com.br.commands.despesa;

import com.br.business.service.DespesaService;
import com.br.business.service.FormaPagamentoService;
import com.br.business.service.FornecedorService;
import com.br.business.service.TipoDespesaService;
import com.br.commands.DefaultComponent;
import com.br.commands.FornecedorComp;
import com.br.config.ShellHelper;
import com.br.entity.Despesa;
import com.br.entity.Fornecedor;
import com.br.filter.DespesaFilter;
import com.br.util.Util;
import com.br.util.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaDespesa {

    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;
    private FornecedorComp fornecedorComp;

    public ConsultaDespesa(DefaultComponent defaultComponent,
                           ShellHelper shellHelper,
                           FornecedorComp fornecedorComp) {

        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
        this.fornecedorComp = fornecedorComp;
    }

//    public void consulta(DespesaService despesaService,
//                         FornecedorService fornecedorService,
//                         TipoDespesaService tipoDespesaService,
//                         FormaPagamentoService formaPagamentoService){
////        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
//        //this.fornecedorComp = new FornecedorComp(terminal, getTemplateExecutor(), getResourceLoader());
//
//        boolean contEdtDespesa = true;
//        DespesaFilter despesaFilter = DespesaFilter.builder().build();
//        int size = 30;
//        int numberPage = 1;
//        int order = 1;
//        String campoOrder = "dataPagamento";
//        Page<Despesa> pg;
//        do{
//            Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
//            Pageable pageable = PageRequest.of(numberPage-1, size, Sort.by(direction, campoOrder));
//            pg = despesaService.listDespesasPaged(despesaFilter, pageable);
//            String header = String.format(" %-6s | %-25s | %-80s | %-10s | %15s | %34s |  %-30s","id", "Tipo Despesa", "Fonrecedor", "Data", "Valor", "Forma Pagamento", "Obs");
//            System.out.print("\033\143");
//            shellHelper.printInfo("-".repeat(230));
//            shellHelper.printInfo(header);
//            shellHelper.printInfo("-".repeat(230));
//            if(pg.hasContent()){
//                pg.getContent().forEach(despesa -> {
//                    String label = String.format(" %-6s | %-25s | %-80s | %-10s | %15s | %34s |  %-30s",
//                            despesa.getId(),
//                            despesa.getTipoDespesa().getNome(),
//                            despesa.getFornecedor().getNome(),
//                            Util.toDatePtBr(despesa.getDataPagamento()),
//                            Util.toCurrencyPtBr(despesa.getValor()),
//                            despesa.getFormaPagamento().getNome(),
//                            despesa.getObs());
//                    shellHelper.printInfo(label);
//                });
//            }else
//                shellHelper.printInfo(" !!! nenhum despesa encontrado... !!!");
//            shellHelper.printInfo("-".repeat(230));
//            BigDecimal total = despesaService.getSumDespesa(despesaFilter);
//            shellHelper.printInfo("");
//            shellHelper.printInfo(String.format(" pagina: %-10s  paginas: %-10s tamanho: %-10s  registros: %-10s  total: %-10s",
//                    numberPage, pg.getTotalPages(), size, pg.getTotalElements(), Util.toCurrencyPtBr(total)));
//            shellHelper.printInfo("");
//            shellHelper.printInfo("Filtros:");
//            if(despesaFilter.getId()!=null) shellHelper.printInfo("	-> id = " + despesaFilter.getId());
//            if(despesaFilter.getTipoDespesa()!=null) shellHelper.printInfo("	-> Tipo despesa = " + despesaFilter.getTipoDespesa().getNome());
//            if(despesaFilter.getFormaPagamento()!=null) shellHelper.printInfo("	-> Forma Pagamento = " + despesaFilter.getFormaPagamento().getNome());
//            if(despesaFilter.getDataInicial()!=null) shellHelper.printInfo("	-> Data Inicial = " + Util.toDatePtBr(despesaFilter.getDataInicial()));
//            if(despesaFilter.getDataFinal()!=null) shellHelper.printInfo("	-> Data Final = " + Util.toDatePtBr(despesaFilter.getDataFinal()));
//            if(despesaFilter.getFornecedor()!=null) shellHelper.printInfo("	-> Fornecedor = "
//                    + fornecedorService.findById(despesaFilter.getFornecedor().getId()).get().getNome());
//            shellHelper.printInfo("");
//            shellHelper.printInfo("paginação > [exit ]  [pag xx    ]  [order xx ]  [size]");
//            shellHelper.printInfo("filtros   > [id xx]  [tipo      ]  [forma    ]  [periodo 00/00/0000 00/00/0000] [fornecedor] [clear]");
//            shellHelper.printInfo("comandos  > [clear]  [excluir xx]  [editar xx]");
//            shellHelper.printInfo("");
//
//            String resposta = this.defaultComponent.inputTextDefault(" ->", "");
//            if(resposta.equals("exit")){
//                contEdtDespesa = false;
//            }else{
//                String[] resparr = resposta.split(" ");
//                switch (resparr[0]){
//                    case "size" :
//                        size = this.defaultComponent.selectPageDefault();
//                        break;
//                    case "pag" : {
//                        if(resparr[1]!=null && resparr[1].matches("[0-9]+")) {
//                            Integer retInt = Integer.parseInt(resparr[1]);
//                            if(retInt>=1 && retInt<=pg.getTotalPages()) {
//                                numberPage = pg.getContent().size() < size ? 1 : retInt;
//                            }else
//                                shellHelper.printError(String.format("valor digitado tem que ser entre 1 e %d",pg.getTotalPages()));
//                        }else
//                            shellHelper.printError("valor digitado nao corresponde a um numero");
//                        break;
//                    }
//                    case "order":{
//                        List<SelectorItem<String>> ltOpc = new ArrayList<>(){{
//                            add(SelectorItem.of("Id", "id"));
//                            add(SelectorItem.of("Tipo Despesa", "tipoDespesa"));
//                            add(SelectorItem.of("Forma Pagamento", "formaPagamento"));
//                            add(SelectorItem.of("Data Pagamento", "dataPagamento"));
//                            add(SelectorItem.of("Fonrecedor", "fornecedor.nome"));
//                        }};
//                        campoOrder = this.defaultComponent.selectDefault(ltOpc, "Selecionar Campo");
//                        List<SelectorItem<String>> opcOrder = new ArrayList<>();
//                        opcOrder.add(SelectorItem.of("Ascesdente", "0"));
//                        opcOrder.add(SelectorItem.of("Descendente", "1"));
//                        order = Integer.valueOf(this.defaultComponent.selectDefault(opcOrder, "Selecionar Ordem Campo"));
//                        break;
//                    }
//                    case "id": {
//                        //String id = re//this.defaultComponent.inputTextDefault(" id ->", "");
//                        if ((resparr[1] != null && resparr[1].matches("[0-9]+"))) {
//                            despesaFilter.setId(Long.valueOf(resparr[1]));
//                        } else {
//                            shellHelper.printError("valor digitado nao corresponde a um numero !");
//                        }
//                        break;
//                    }
//					case "tipo":
//                        despesaFilter.setTipoDespesa(this.defaultComponent.selectTipoDespesa(tipoDespesaService.findTipoDespesas()));
//                        break;
//					case "forma":
//                        despesaFilter.setFormaPagamento(this.defaultComponent.selectFormaPagamento(formaPagamentoService.findFormasPagamento()));
//                        break;
//                    case "periodo":{
//                        if(resparr[1]!=null && Validate.isValidDate(resparr[1])) {
//                            despesaFilter.setDataInicial(Util.getData(resparr[1]));
//                            if(resparr.length==3 && Validate.isValidDate(resparr[2]))
//                                despesaFilter.setDataFinal(Util.getData(resparr[2]));
//                            else
//                                shellHelper.printError("data final inválida!");
//                        }else
//                            shellHelper.printError("data incial inválida!");
//                        break;
//                    }
//                    case "fornecedor":{
//                        despesaFilter.setFornecedor(new Fornecedor());
//                        String fornec = fornecedorComp.selectTableFornecedor(fornecedorService.findFornecedores());
//                        despesaFilter.getFornecedor().setId(new BigInteger(fornec));
//                        break;
//                    }
//                    case "editar" :{
//                        if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
//                            Optional<Despesa> optionalDespesa = despesaService.findById(new BigInteger(resparr[1].toString()));
//                            if(optionalDespesa.isEmpty())
//                                shellHelper.printError("Id da despesa não econtrado !");
//                            else
//                                editar(optionalDespesa.get(),
//                                        tipoDespesaService,
//                                        formaPagamentoService,
//                                        despesaService,
//                                        fornecedorService);
//                        }else
//                            shellHelper.printError("valor digitado nao corresponde a um numero");
//                        this.defaultComponent.confirmationInput("enter para continuar", true);
//                        break;
//                    }
//                    case "clear": {
//                        despesaFilter = DespesaFilter.builder().build();
//                        break;
//                    }
//                    default: numberPage = pg.getContent().size() < size ? 1 : numberPage+1;
//                }
//            }
//        }while (!!contEdtDespesa);
//    }
//
//    public void editar(Despesa despesa,
//                       TipoDespesaService tipoDespesaService,
//                       FormaPagamentoService formaPagamentoService,
//                       DespesaService despesaService,
//                       FornecedorService fornecedorService) {
//		System.out.print("\033\143");
//
//		this.showDespesa(despesa);
//
//		if(defaultComponent.confirmationInput("Alterar Tipo Despesa", false))
//			despesa.setTipoDespesa(this.defaultComponent.selectTipoDespesa(tipoDespesaService.findTipoDespesas()));
//		if(defaultComponent.confirmationInput("Alterar Fornecedor", false))
//            despesa.setFornecedor(this.defaultComponent.selectFornecedor(fornecedorService.findFornecedores()));
//		if(defaultComponent.confirmationInput("Alterar Data Pagamento", false))
//			despesa.setDataPagamento(Util.getData(this.defaultComponent.inData("Data Pagamento")));
//		if(defaultComponent.confirmationInput("Alterar Valor", false))
//            despesa.setValor(new BigDecimal(defaultComponent.inValor("Valor")));
//		if(defaultComponent.confirmationInput("Alterar Forma Pagamento", false))
//            despesa.setFormaPagamento(this.defaultComponent.selectFormaPagamento(formaPagamentoService.findFormasPagamento()));
//		if(defaultComponent.confirmationInput("Alterar Observação", false))
//			despesa.setObs(this.defaultComponent.inputoObs());
//
//        System.out.print("\033\143");
//		this.showDespesa(despesa);
//		if(this.defaultComponent.confirmationInput("Salvar Despesa", true)){
//			despesaService.save(despesa);
//		}
//    }
//
//    public void showDespesa(Despesa despesa){
//        shellHelper.printInfo("+"+"-".repeat(100));
//        shellHelper.printInfo(String.format("| %-15s %s","Tipo Despesa: ", despesa.getTipoDespesa()));
//        shellHelper.printInfo(
//                String.format("| %-15s %s - %s - %s-%s", "Fornecedor: ",
//                        despesa.getFornecedor().getNome(),
//                        despesa.getFornecedor().getCnpj(),
//                        despesa.getFornecedor().getCidade().getNome(),
//                        despesa.getFornecedor().getCidade().getUf()));
//        shellHelper.printInfo(String.format("| %-15s %s", "Data: ", Util.toDatePtBr(despesa.getDataPagamento())));
//        shellHelper.printInfo(String.format("| %-15s %s", "Valor: ", Util.toCurrencyPtBr(despesa.getValor())));
//        shellHelper.printInfo(String.format("| %-15s %s", "Forma Pgto: ", despesa.getFormaPagamento()));
//        shellHelper.printInfo(String.format("| %-15s %s", "Obs: ", despesa.getObs()));
//        shellHelper.printInfo("+"+"-".repeat(100));
//	}
}
