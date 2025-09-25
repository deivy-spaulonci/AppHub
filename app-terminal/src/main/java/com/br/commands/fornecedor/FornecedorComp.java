package com.br.commands.fornecedor;

import com.br.entity.Fornecedor;
import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.style.TemplateExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class FornecedorComp {

    private Terminal terminal;
    private ResourceLoader resourceLoader;
    private TemplateExecutor templateExecutor;

    public FornecedorComp(Terminal terminal,
                            TemplateExecutor templateExecutor,
                            ResourceLoader resourceLoader){
        this.terminal = terminal;
        this.templateExecutor = templateExecutor;
        this.resourceLoader = resourceLoader;
    }

    public String selectTableFornecedor(List<Fornecedor> fornecedorList){
        List<SelectorItem<String>> itens = new ArrayList<>();
        fornecedorList.sort((f1, f2) -> f1.getNome().compareTo(f2.getNome()));
        fornecedorList.forEach(fornecedor -> {
            String label = String.format("%-6s | %-65s | %-65s | %-15s | %-15s | %65s",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getRazaoSocial(),
                    fornecedor.getCnpj(),
                    fornecedor.getCpf(),
                    (fornecedor.getCidade().getNome() +" - "+ fornecedor.getCidade().getUf())
            );
            itens.add(SelectorItem.of(label, fornecedor.getId().toString()));
        });

        SingleItemSelector<String, SelectorItem<String>> component = new SingleItemSelector<>(
                getTerminal(),
                itens,
                "Fornecedores",
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(15);
        SingleItemSelector.SingleItemSelectorContext<String, SelectorItem<String>>
                context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        String result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;

    }
}
