package com.br.commands.fornecedor;

import com.br.business.service.ContaService;
import com.br.business.service.FornecedorService;
import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.entity.Fornecedor;
import com.br.filter.ContaFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ConsultaFornecedor {

    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;

    public ConsultaFornecedor(DefaultComponent defaultComponent,
                              ShellHelper shellHelper) {

        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
    }

    public Page<Fornecedor> loadTable(int order, int size, int numberPage, String campoSort, String busca, FornecedorService fornecedorService){
        Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(numberPage-1, size, Sort.by(direction, campoSort));
        System.out.println(busca);
        Page<Fornecedor> pg = fornecedorService.listFornecedoresPaged(busca, pageable);
        String header = String.format("| %-6s | %-80s | %-80s | %20s | %40s","id", "Nome", "Razão Social", "CPF/CNPJ", "Cidade");
        System.out.print("\033\143");
        shellHelper.printInfo("-".repeat(250));
        shellHelper.printInfo(header);
        shellHelper.printInfo("-".repeat(250));
        if(pg.hasContent()){
            pg.getContent().forEach(fornec -> {
                String label = String.format("| %-6s | %-80s | %-80s | %20s | %40s",
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
        shellHelper.printInfo("Paginação : [exit ]  [pg   xx] [order   ] [size]");
        shellHelper.printInfo("Comandos  : [clear]  [edit xx] [busca xx]");
        shellHelper.printInfo("");
        shellHelper.printInfo(busca!=null ? "Busca = " + busca : "");
        return pg;
    }

    public void consulta(FornecedorService fornecedorService) {
        var contFornec = true;
        int size = 30;
        int numberPage = 1;
        int order = 1;
        String campoOrder = "nome";
        String busca = null;
        do{

            Page<Fornecedor> page = loadTable(order,size,numberPage,campoOrder,busca,fornecedorService);

            String resposta = this.defaultComponent.inputTextDefault(" ->", "");
            if(resposta.equals("exit")){
                contFornec = false;
            }else{
                String[] resparr = resposta.split(" ");
                switch (resparr[0]) {
                    case "size":
                        size = this.defaultComponent.selectPageDefault();
                        break;
                    case "pg": {
                        if (resparr[1].matches("[0-9]+")) {
                            Integer retInt = Integer.parseInt(resparr[1]);
                            if (retInt >= 1 && retInt <= page.getTotalPages())
                                numberPage = page.getContent().size() < size ? 1 : retInt;
                            else
                                shellHelper.printError(String.format("valor digitado tem que ser entre 1 e %d", page.getTotalPages()));
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
                    default: numberPage = page.getContent().size() < size ? 1 : numberPage+1;
                }
            }
        }while(!!contFornec);
    }
}
