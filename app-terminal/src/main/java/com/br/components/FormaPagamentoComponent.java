package com.br.components;

import com.br.business.service.FormaPagamentoService;
import com.br.commands.DefaultComponent;
import com.br.dto.response.FormaPagamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class FormaPagamentoComponent {

    private FormaPagamentoService formaPagamentoService;
    private final DefaultComponent defaultComponent;

    @Autowired
    public FormaPagamentoComponent(FormaPagamentoService formaPagamentoService,
                                   DefaultComponent defaultComponent) {
        this.defaultComponent = defaultComponent;
        this.formaPagamentoService = formaPagamentoService;
    }

    public FormaPagamentoResponseDTO sellectFormaPagamento() {
        List<SelectorItem<FormaPagamentoResponseDTO>> formasPagamentos = new ArrayList<>();
        formaPagamentoService.findFormasPagamento().forEach(forma ->{
            formasPagamentos.add(SelectorItem.of(forma.getNome(),forma));
        });
        formasPagamentos.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<FormaPagamentoResponseDTO, SelectorItem<FormaPagamentoResponseDTO>> component = new SingleItemSelector<>(
                defaultComponent.terminal,
                formasPagamentos,
                "Forma de Pagamento: ",
                null);
        component.setResourceLoader(defaultComponent.resourceLoader);
        component.setTemplateExecutor(defaultComponent.templateExecutor);
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<FormaPagamentoResponseDTO, SelectorItem<FormaPagamentoResponseDTO>> context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        FormaPagamentoResponseDTO result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;
    }
}
