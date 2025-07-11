package com.br.commands;

import com.br.business.service.DespesaService;
import com.br.business.service.FormaPagamentoService;
import com.br.business.service.TipoDespesaService;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoConta;
import com.br.entity.TipoDespesa;
import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.ConfirmationInput;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.style.TemplateExecutor;

import java.math.BigInteger;
import java.util.*;

@Getter
@Setter
public class DefaultComponent {
    private Terminal terminal;
    private TemplateExecutor templateExecutor;
    private ResourceLoader resourceLoader;

    public DefaultComponent(Terminal terminal,
                            TemplateExecutor templateExecutor,
                            ResourceLoader resourceLoader){
        this.terminal = terminal;
        this.templateExecutor = templateExecutor;
        this.resourceLoader = resourceLoader;
    }

    public String selectFormaPagamento(FormaPagamentoService formaPagamentoService) {
        List<SelectorItem<String>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        formaPagamentoService.findFormasPagamento().forEach(forma -> {
            items.add(SelectorItem.of(forma.getNome(), forma.getId().toString()));
        });

        return selectDefault(items, "Forma Pagamento");
    }

    public String selectTipoDespesa(TipoDespesaService tipoDespesaService) {
        List<SelectorItem<String>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        tipoDespesaService.findTipoDespesas().forEach(despesa -> {
            items.add(SelectorItem.of(despesa.getNome(), despesa.getId().toString()));
        });
        items.sort(Comparator.comparing(SelectorItem::getName));
        return selectDefault(items, "Tipo Despesa :");
    }

    public Integer selectPageDefault() {
        List<SelectorItem<Integer>> items = new ArrayList<>();
        items.add(SelectorItem.of("10", 10));
        items.add(SelectorItem.of("15", 15));
        items.add(SelectorItem.of("20", 20));
        items.add(SelectorItem.of("25", 25));
        items.add(SelectorItem.of("30", 30));
        items.add(SelectorItem.of("40", 40));
        items.add(SelectorItem.of("50", 50));
        return selectDefaultInt(items, "Tamanho da Pagina:");
    }

    public Integer selectDefaultInt(List<SelectorItem<Integer>> itens, String label){
        SingleItemSelector<Integer, SelectorItem<Integer>> component = new SingleItemSelector<>(getTerminal(),itens,label,null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(20);

        SingleItemSelector.SingleItemSelectorContext<Integer, SelectorItem<Integer>> context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        Integer result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;
    }

//    public String selectDefault(List<SelectorItem<String>> itens, String label, int size){
//        SingleItemSelector<String, SelectorItem<String>> component = new SingleItemSelector<>(
//                getTerminal(),
//                itens,
//                label,
//                null);
//        component.setResourceLoader(getResourceLoader());
//        component.setTemplateExecutor(getTemplateExecutor());
//        component.setMaxItems(20);
//        SingleItemSelector.SingleItemSelectorContext<String, SelectorItem<String>> context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
//        String result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
//        return result;
//    }

    public String selectDefault(List<SelectorItem<String>> itens, String label){
        SingleItemSelector<String, SelectorItem<String>> component = new SingleItemSelector<>(
                getTerminal(),
                itens,
                label,
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<String, SelectorItem<String>> context =
                component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        String result = context.getResultItem().flatMap(si ->
                Optional.ofNullable(si.getItem())).get();
        return result;
    }

    public Integer inputInteger(String label){
        StringInput component = new StringInput(getTerminal(), label, "");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return Integer.valueOf(context.getResultValue());
    }

    public String inputValor(){
        StringInput component = new StringInput(getTerminal(), "Valor: ", "");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setPrintResults(false);
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return context.getResultValue();
    }

    public String inputCnpjFornecedor(){
        StringInput component = new StringInput(getTerminal(), "Fornecedor (CNPJ)", "");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setPrintResults(false);
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return context.getResultValue();
    }

    public String inputTextDefault(String label, String defaultValue){
        try{
            StringInput component = new StringInput(getTerminal(), label, defaultValue);
            component.setResourceLoader(getResourceLoader());
            component.setTemplateExecutor(getTemplateExecutor());
            component.setPrintResults(false);
            StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
            return context.getResultValue();
        }catch (Exception e){
            return "";
        }
    }

    public String inputoObs(){
        StringInput component = new StringInput(getTerminal(), "Observação: ", "");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setPrintResults(false);
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return context.getResultValue();
    }

    //	@ShellMethod("Change password.")
//	public String inputData(@Size(min = 10, max = 10) String data){
    public String inputData(){
        StringInput component = new StringInput(getTerminal(), "Data ddMMyyyy", "");
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());

        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        return context.getResultValue();
    }

    public boolean confirmationInput(String msg, boolean def) {
        ConfirmationInput component = new ConfirmationInput(getTerminal(), String.format("%s ?", msg), def);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setPrintResults(false);
        ConfirmationInput.ConfirmationInputContext context = component.run(ConfirmationInput.ConfirmationInputContext.empty());
        return context.getResultValue();
    }

}
