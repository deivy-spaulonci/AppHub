package com.br.commands;

import com.br.business.service.DespesaService;
import com.br.business.service.FornecedorService;
import com.br.business.service.TipoContaService;
import com.br.config.ShellHelper;
import com.br.entity.Fornecedor;
import com.br.filter.DespesaFilter;
import com.br.loading.ProgressCounter;
import com.br.util.Util;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@ShellComponent
public class FornecedorCommands extends AbstractShellComponent {
    @Autowired
    private ComponentFlow.Builder componentFlowBuilder;
    @Setter
    @Getter
    @Autowired
    private Terminal terminal;
    @Autowired
    private FornecedorService fornecedorService;
    private DefaultComponent defaultComponent;
    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    ProgressCounter progressCounter;

    @ShellMethod("Consulta Fornecedor")
    public void findFornecedor() {
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());

        var contFornec = true;
        int size = 20;
        int numberPage = 1;
        int order = 1;
        String campoOrder = "nome";
        Page<Fornecedor> pg;
        String busca = null;
        do{
            Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
            Pageable pageable = PageRequest.of(numberPage-1, size, Sort.by(direction, campoOrder));
            pg = fornecedorService.listFornecedoresPaged(busca, pageable);
            String header = String.format(" %-6s | %-80s | %-80s | %20s | %40s","id", "Nome", "Razão Social", "CPF/CNPJ", "Cidade");
            System.out.print("\033\143");
            shellHelper.printInfo("-".repeat(250));
            shellHelper.printInfo(header);
            shellHelper.printInfo("-".repeat(250));
            if(pg.hasContent()){
                pg.getContent().forEach(fornec -> {
                    String label = String.format(" %-6s | %-80s | %-80s | %20s | %40s",
                            fornec.getId(),
                            fornec.getNome(),
                            fornec.getRazaoSocial(),
                            (fornec.getCnpj() == null ? fornec.getCpf() : fornec.getCnpj()),
                            fornec.getCidade().getNome()+"-"+fornec.getCidade().getUf());
                    shellHelper.printInfo(label);
                });
            }else
                shellHelper.printInfo(" !!! nenhum fornecedor encontrado... !!!");
            shellHelper.printInfo("-".repeat(250));
            shellHelper.printInfo("");
            shellHelper.printInfo(String.format(" pagina: %-10s  paginas: %-10s tamanho: %-10s  registros: %-10s",
                    numberPage, pg.getTotalPages(), size, pg.getTotalElements()));
            shellHelper.printInfo("");
            shellHelper.printInfo("paginação > [exit]   [pag xx]    [order]    [size]");
            shellHelper.printInfo("comandos  > [clear]  [editar xx] [busca xx]");
            shellHelper.printInfo("");
            shellHelper.printInfo("Busca = " + busca);

            String resposta = this.defaultComponent.inputTextDefault(" ->", "");
            if(resposta.equals("exit")){
                contFornec = false;
            }else{
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
                        List<SelectorItem<String>> ltOpc = new ArrayList<>(){{
                            add(SelectorItem.of("Id", "id"));
                            add(SelectorItem.of("Nome", "nome"));
                            add(SelectorItem.of("Razão Social", "razaoSocial"));
                        }};
                        campoOrder = this.defaultComponent.selectDefault(ltOpc, "Selecionar Campo");
                        List<SelectorItem<String>> opcOrder = new ArrayList<>();
                        opcOrder.add(SelectorItem.of("Ascesdente", "0"));
                        opcOrder.add(SelectorItem.of("Descendente", "1"));
                        order = Integer.valueOf(this.defaultComponent.selectDefault(opcOrder, "Selecionar Ordem Campo"));
                        break;
                    }
                    case "clear": busca = "";  break;
                    case "busca": busca = resparr[1]; break;
                    default: numberPage = pg.getContent().size() < size ? 1 : numberPage+1;
                }
            }
        }while(!!contFornec);
    }


    @ShellMethod("Cadastro Fornecedor")
    public void addFornecedor() {
        this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
        var contForn = true;
        String forn = "";
        Fornecedor f = null;
        do{
            forn = this.defaultComponent.inputCnpjFornecedor();
            f = fornecedorService.findByCnpj(forn);

            if(Objects.nonNull(f)){
                this.printFornecedor(f);
                contForn = (Objects.nonNull(f) ? this.defaultComponent.confirmationInput("Fornecedor ja cadastrado! Tentar novamente?", true) : false);
            }else{
                String finalForn = forn;
                shellHelper.printInfo("Buscando dados na web...");
//                progressCounter.display();
                f = fornecedorService.getFornecedorFromWeb(finalForn);
//                progressCounter.reset();

                if(Objects.nonNull(f)){
                    this.printFornecedorApi(f);
                    contForn = this.defaultComponent.confirmationInput("Cadastrar fornecedor?", true);
                    if(contForn){
                        fornecedorService.save(f);
                        contForn=false;
                    }
                }

            }
        } while (contForn == true);
        if(forn.isEmpty())
            return;
    }

    public void printFornecedorApi(Fornecedor f){
        shellHelper.printInfo("Fornecedor da api!");
        shellHelper.printInfo("Nome_________: " + f.getNome());
        shellHelper.printInfo("Razão Social_: " + f.getRazaoSocial());
        shellHelper.printInfo("CNPJ / CPF __: " + (Objects.nonNull(f.getCnpj()) ? f.getCnpj() : f.getCpf()));
        shellHelper.printInfo("Ibge_________: " + f.getCidade().getIbgeCod());
    }

    public void printFornecedor(Fornecedor f){
        shellHelper.printInfo("Fornecedor já cadastrado!");
        shellHelper.printInfo("Nome_________: " + f.getNome());
        shellHelper.printInfo("Razão Social_: " + f.getRazaoSocial());
        shellHelper.printInfo("CNPJ / CPF __: " + (Objects.nonNull(f.getCnpj()) ? f.getCnpj() : f.getCpf()));
        shellHelper.printInfo("Cidade_______: " + f.getCidade().getNome());
    }



}
