package com.br.components;

import com.br.business.service.TipoDespesaService;
import com.br.commands.DefaultComponent;
import com.br.dto.response.TipoDespesaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class TipoDespesaComponent {

    private TipoDespesaService tipoDespesaService;
    private final DefaultComponent defaultComponent;

    @Autowired
    public TipoDespesaComponent(DefaultComponent defaultComponent,
                                TipoDespesaService tipoDespesaService) {
        this.defaultComponent = defaultComponent;
        this.tipoDespesaService = tipoDespesaService;
    }

    public TipoDespesaResponseDTO selectTipoDespesa() {
        List<SelectorItem<TipoDespesaResponseDTO>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        tipoDespesaService.findTipoDespesas().forEach(tipo -> {
            items.add(SelectorItem.of(tipo.getNome(), tipo));
        });
        items.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<TipoDespesaResponseDTO, SelectorItem<TipoDespesaResponseDTO>> component = new SingleItemSelector<>(
                defaultComponent.terminal,
                items,
                "Tipo Despesa: ",
                null);
        component.setResourceLoader(defaultComponent.resourceLoader);
        component.setTemplateExecutor(defaultComponent.templateExecutor);
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<TipoDespesaResponseDTO, SelectorItem<TipoDespesaResponseDTO>> context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        TipoDespesaResponseDTO result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;
    }
}
