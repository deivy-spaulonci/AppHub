package com.br.commands;

import com.br.business.service.TipoDespesaService;
import com.br.components.AbstractDefaultComponent;
import com.br.config.ShellHelper;
import com.br.util.Validate;
import lombok.Getter;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.ConfirmationInput;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.support.AbstractComponent;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.style.TemplateExecutor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ShellComponent
public class DefaultComponent extends AbstractDefaultComponent {

    @Autowired
    public DefaultComponent(ComponentFlow.Builder componentFlowBuilder, Terminal terminal, TemplateExecutor templateExecutor, ResourceLoader resourceLoader, ShellHelper shellHelper) {
        super(componentFlowBuilder, terminal, templateExecutor, resourceLoader, shellHelper);
    }

//    @Autowired
//    public DefaultComponent(ComponentFlow.Builder componentFlowBuilder,
//                            Terminal terminal,
//                            TemplateExecutor templateExecutor,
//                            ResourceLoader resourceLoader,
//                            ShellHelper shellHelper){
//        this.componentFlowBuilder = componentFlowBuilder;
//        this.terminal = terminal;
//        this.templateExecutor = templateExecutor;
//        this.resourceLoader = resourceLoader;
//        this.shellHelper = shellHelper;
//    }


//    public Fornecedor selectFornecedor(List<Fornecedor> fornecedores) {
//        List<SelectorItem<Fornecedor>> items = new ArrayList<>();//Arrays.asList(i1, i2, i3, i4);
//        fornecedores.forEach(fornec -> {
//            items.add(SelectorItem.of(fornec.getNome(), fornec));
//        });
//        items.sort(Comparator.comparing(SelectorItem::getName));
//        SingleItemSelector<Fornecedor, SelectorItem<Fornecedor>> component = new SingleItemSelector<>(
//                getTerminal(),
//                items,
//                "Fornecedor: ",
//                null);
//        component.setResourceLoader(getResourceLoader());
//        component.setTemplateExecutor(getTemplateExecutor());
//        component.setMaxItems(25);
//        component.setPrintResults(false);
//        SingleItemSelector.SingleItemSelectorContext<Fornecedor, SelectorItem<Fornecedor>> context =
//                component.run(SingleItemSelector.SingleItemSelectorContext.empty());
//        Fornecedor result = context.getResultItem().flatMap(si ->
//                Optional.ofNullable(si.getItem())).get();
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

//    public Integer inputInteger(String label, String deault){
//        StringInput component = new StringInput(getTerminal(), label, deault);
//        component.setResourceLoader(getResourceLoader());
//        component.setTemplateExecutor(getTemplateExecutor());
//        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
//        if(context.getResultValue()!=null && context.getResultValue().matches("[0-9]+"))
//            return Integer.valueOf(context.getResultValue());
//
//        return Integer.valueOf(deault);
//    }

    public Integer inputInteger(String label, String defaultValue) {
        StringInput component = new StringInput(getTerminal(), label, defaultValue);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());

        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
        String input = context.getResultValue();

        // Valida a entrada
        if (input != null) {
            input = input.trim(); // remove espaços

            // Verifica se é um número inteiro válido
            if (input.matches("\\d+")) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // caso o número seja grande demais pra Integer
                    shellHelper.prWarning("⚠ Valor fora do intervalo permitido para inteiros.");
                }
            } else {
                shellHelper.prWarning("⚠ Entrada inválida: apenas números são permitidos.");
            }
        } else {
            shellHelper.prWarning("⚠ Nenhum valor informado, usando padrão.");
        }

        // Retorna o valor padrão se inválido
        try {
            return Integer.parseInt(defaultValue.trim());
        } catch (NumberFormatException e) {
            shellHelper.prWarning("⚠ Valor padrão inválido. Retornando 0.");
            return 0;
        }
    }


//    public String inputCnpjFornecedor(){
//        StringInput component = new StringInput(getTerminal(), "Fornecedor (CNPJ)", "");
//        component.setResourceLoader(getResourceLoader());
//        component.setTemplateExecutor(getTemplateExecutor());
//        component.setPrintResults(false);
//        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
//        return context.getResultValue();
//    }

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

//    //	@ShellMethod("Change password.")
////	public String inputData(@Size(min = 10, max = 10) String data){
////    public String inputData(){
////        StringInput component = new StringInput(getTerminal(), "Data ddMMyyyy", "");
////        component.setResourceLoader(getResourceLoader());
////        component.setTemplateExecutor(getTemplateExecutor());
////
////        StringInput.StringInputContext context = component.run(StringInput.StringInputContext.empty());
////        return context.getResultValue();
////    }

    public boolean confirmationInput(String msg, boolean def) {
        ConfirmationInput component = new ConfirmationInput(getTerminal(), String.format("%s ", msg), def);
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
