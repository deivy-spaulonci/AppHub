package com.br.commands;

import com.br.config.ShellHelper;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoConta;
import com.br.util.Validate;
import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.ConfirmationInput;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.style.TemplateExecutor;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
public class DefaultComponent {
    private Terminal terminal;
    private TemplateExecutor templateExecutor;
    private ResourceLoader resourceLoader;
    private ShellHelper shellHelper;

    public DefaultComponent(Terminal terminal,
                            TemplateExecutor templateExecutor,
                            ResourceLoader resourceLoader){
        this.terminal = terminal;
        this.templateExecutor = templateExecutor;
        this.resourceLoader = resourceLoader;
        this.shellHelper = new ShellHelper(terminal);
    }

    public FormaPagamento selectFormaPagamento(List<FormaPagamento> formas) {
        List<SelectorItem<FormaPagamento>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        formas.forEach(forma -> {
            items.add(SelectorItem.of(forma.getNome(), forma));
        });
//        return selectDefaultTipo(items, "Forma Pagamento");
        items.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<FormaPagamento, SelectorItem<FormaPagamento>> component = new SingleItemSelector<>(
                getTerminal(),
                items,
                "Forma Pagamento: ",
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<FormaPagamento, SelectorItem<FormaPagamento>> context =
                component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        FormaPagamento result = context.getResultItem().flatMap(si ->
                Optional.ofNullable(si.getItem())).get();
        return result;
    }

    public TipoDespesaResponseDTO selectTipoDespesa(List<TipoDespesaResponseDTO> tipos) {
        List<SelectorItem<TipoDespesaResponseDTO>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        tipos.forEach(tipo -> {
            items.add(SelectorItem.of(tipo.getNome(), tipo));
        });
        items.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<TipoDespesaResponseDTO, SelectorItem<TipoDespesaResponseDTO>> component = new SingleItemSelector<>(
                getTerminal(),
                items,
                "Tipo Despesa: ",
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<TipoDespesaResponseDTO, SelectorItem<TipoDespesaResponseDTO>> context = component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        TipoDespesaResponseDTO result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
        return result;
    }

    public Fornecedor selectFornecedor(List<Fornecedor> fornecedores) {
        List<SelectorItem<Fornecedor>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        fornecedores.forEach(fornec -> {
            items.add(SelectorItem.of(fornec.getNome(), fornec));
        });
        items.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<Fornecedor, SelectorItem<Fornecedor>> component = new SingleItemSelector<>(
                getTerminal(),
                items,
                "Fornecedor: ",
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<Fornecedor, SelectorItem<Fornecedor>> context =
                component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        Fornecedor result = context.getResultItem().flatMap(si ->
                Optional.ofNullable(si.getItem())).get();
        return result;
    }

    public TipoConta selectTipoConta(List<TipoConta> tipos) {
        List<SelectorItem<TipoConta>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
        tipos.forEach(tipo -> {
            items.add(SelectorItem.of(tipo.getNome(), tipo));
        });
        items.sort(Comparator.comparing(SelectorItem::getName));
        SingleItemSelector<TipoConta, SelectorItem<TipoConta>> component = new SingleItemSelector<>(
                getTerminal(),
                items,
                "Tipo Conta: ",
                null);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setMaxItems(25);
        component.setPrintResults(false);
        SingleItemSelector.SingleItemSelectorContext<TipoConta, SelectorItem<TipoConta>> context =
                component.run(SingleItemSelector.SingleItemSelectorContext.empty());
        TipoConta result = context.getResultItem().flatMap(si ->
                Optional.ofNullable(si.getItem())).get();
        return result;
    }

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

    public Integer inputInteger(String label, String deault){
        StringInput component = new StringInput(getTerminal(), label, deault);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        if(context.getResultValue()!=null && context.getResultValue().matches("[0-9]+"))
            return Integer.valueOf(context.getResultValue());
        return Integer.valueOf(deault);
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
//    public String inputData(){
//        StringInput component = new StringInput(getTerminal(), "Data ddMMyyyy", "");
//        component.setResourceLoader(getResourceLoader());
//        component.setTemplateExecutor(getTemplateExecutor());
//
//        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
//        return context.getResultValue();
//    }

    public boolean confirmationInput(String msg, boolean def) {
        ConfirmationInput component = new ConfirmationInput(getTerminal(), String.format("%s ?", msg), def);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());
        component.setPrintResults(false);
        ConfirmationInput.ConfirmationInputContext context = component.run(ConfirmationInput.ConfirmationInputContext.empty());
        return context.getResultValue();
    }

    public String inputData(String label){
        boolean valid = false;
        String data = "";
        do{
            StringInput component = new StringInput(terminal, " %s [ddMMyyyy] ".formatted(label), "");
            component.setResourceLoader(getResourceLoader());
            component.setTemplateExecutor(getTemplateExecutor());
            component.setPrintResults(false);
            StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
            data = context.getResultValue();
            valid = !data.isEmpty() && Validate.isValidDate(data);

        } while (valid ? false :  confirmationInput('⛔' + " %s inválida! Continuar".formatted(label), true));
        return data;
    }

    public String inputValor(String label){
        boolean valid = false;
        String valor = "";
        do{
            StringInput component = new StringInput(getTerminal(), "%s: ".formatted(label), "");
            component.setResourceLoader(getResourceLoader());
            component.setTemplateExecutor(getTemplateExecutor());
            component.setPrintResults(false);
            StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
            valor = context.getResultValue().replaceAll(",", ".");

            try{
                valid = new BigDecimal(valor).compareTo(BigDecimal.ZERO) == 1;
            }catch(NumberFormatException e){
                valid  = false;
            }
        }while (valid ? false : confirmationInput('⛔' + "%s inválido! Continuar".formatted(label), true));
        return valor;

    }


}
