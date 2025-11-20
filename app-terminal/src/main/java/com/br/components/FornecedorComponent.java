package com.br.components;

import com.br.business.service.CidadeService;
import com.br.business.service.FornecedorService;
import com.br.commands.DefaultComponent;
import com.br.dto.response.FornecedorResponseDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ShellComponent
public class FornecedorComponent {

    private final DefaultComponent defaultComponent;
    private FornecedorService fornecedorService;
    private CidadeService cidadeService;

    @Autowired
    public FornecedorComponent(DefaultComponent defaultComponent,
                               FornecedorService fornecedorService,
                               CidadeService cidadeService) {
        this.defaultComponent = defaultComponent;
        this.fornecedorService = fornecedorService;
        this.cidadeService = cidadeService;
    }

    public FornecedorResponseDTO selectTableFornecedor(){
        List<SelectorItem<FornecedorResponseDTO>> itens = new ArrayList<>();
        List<FornecedorResponseDTO>  lista = fornecedorService.findFornecedores();
        lista.sort((f1, f2) -> f1.getNome().compareTo(f2.getNome()));
        lista.forEach(fornecedor -> {
            String label = String.format("%-6s | %-65s | %-65s | %-15s | %-15s | %65s",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getRazaoSocial(),
                    fornecedor.getCnpj(),
                    fornecedor.getCpf(),
                    (fornecedor.getCidade().getNome() +" - "+ fornecedor.getCidade().getUf())
            );
            itens.add(SelectorItem.of(label, fornecedor));
        });

        SingleItemSelector<FornecedorResponseDTO, SelectorItem<FornecedorResponseDTO>> component = new SingleItemSelector<>(
                defaultComponent.getTerminal(),
                itens,
                "Fornecedores",
                null);
        component.setResourceLoader(defaultComponent.getResourceLoader());
        component.setTemplateExecutor(defaultComponent.getTemplateExecutor());
        component.setMaxItems(20);
        SingleItemSelector.SingleItemSelectorContext<FornecedorResponseDTO, SelectorItem<FornecedorResponseDTO>>
                context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        FornecedorResponseDTO result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;

    }
}
