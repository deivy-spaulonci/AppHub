package com.br.commands;

import java.util.*;

import com.br.business.service.ContaService;
import com.br.business.service.TipoContaService;
import com.br.config.ShellHelper;
import com.br.entity.TipoDespesa;
import com.br.loading.ProgressBar;
import com.br.loading.ProgressCounter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.SingleItemSelector.SingleItemSelectorContext;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.ResultMode;
import org.springframework.shell.component.flow.SelectItem;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.component.flow.ComponentFlow.ComponentFlowResult;

@Log4j2
@ShellComponent
public class MyCommands extends AbstractShellComponent {

	@Autowired
	ShellHelper shellHelper;

	@Autowired
	ProgressCounter progressCounter;

	@Autowired
	ProgressBar progressBar;
	
	@Autowired
	private ComponentFlow.Builder componentFlowBuilder;

	@Autowired
	private TipoContaService tipoContaService;

//	@ShellMethod("Displays progress counter (with spinner)")
//	public void progressCounter() throws InterruptedException {
//		for (int i = 1; i <= 100; i++) {
//			progressCounter.display(i, "Processing");
//			Thread.sleep(100);
//		}
//		progressCounter.reset();
//	}

//	@ShellMethod("Displays progress counter (with spinner)")
//	public void progressCounter() throws InterruptedException {
//		for (int i = 1; i <= 100; i++) {
//			progressCounter.display();
//			Thread.sleep(100);
//		}
//		progressCounter.reset();
//	}
//
//	@ShellMethod("Displays progress bar")
//	public void progressBar() throws InterruptedException {
//		for (int i = 1; i <= 100; i++) {
//			progressBar.display(i);
//			Thread.sleep(100);
//		}
//		progressBar.reset();
//	}
//
//	@ShellMethod(key = "teste")
//	public String helloWorld(@ShellOption(defaultValue = "spring") String arg) {
//		String output = shellHelper.getSuccessMessage(String.format("Hello %s!", arg));
//		Arrays.stream(TipoDespesa.values()).toList().forEach(tipoDespesa -> log.warn(tipoDespesa.getValue()));
//		return "Hello world " + output;
//	}
//
//	@ShellMethod(key = "hello-world-teste")
//	public String helloWorldTeste(@ShellOption(defaultValue = "spring") String arg) {
//		return "Hello world teste" + this.singleSelector();
//	}
//
//	@ShellMethod(key = "component single", value = "Single selector", group = "Components")
//	public String singleSelector() {
//		SelectorItem<String> i1 = SelectorItem.of("key1", "value1");
//		SelectorItem<String> i2 = SelectorItem.of("key2", "value2");
//		SelectorItem<String> i3 = SelectorItem.of("key3", "value3");
//		SelectorItem<String> i4 = SelectorItem.of("key4", "value4");
//		List<SelectorItem<String>> items = Arrays.asList(i1, i2, i3, i4);
//		SingleItemSelector<String, SelectorItem<String>> component = new SingleItemSelector<>(getTerminal(), items,"testSimple", null);
//		component.setResourceLoader(getResourceLoader());
//		component.setTemplateExecutor(getTemplateExecutor());
//		SingleItemSelectorContext<String, SelectorItem<String>> context = component.run(SingleItemSelectorContext.empty());
//		String result = context.getResultItem().flatMap(si -> Optional.ofNullable(si.getItem())).get();
//		return "Got value " + result;
//	}
//
//	@ShellMethod(key = "flow")
//	public void testeFlow() {
//		Map<String, String> single1SelectItems = new HashMap<>();
//		single1SelectItems.put("key1", "value1");
//		single1SelectItems.put("key2", "value2");
//		List<SelectItem> multi1SelectItems = Arrays.asList(
//				SelectItem.of("key1", "value1"),
//				SelectItem.of("key2", "value2"),
//				SelectItem.of("key3", "value3"));
//		ComponentFlow flow = componentFlowBuilder.clone().reset()
//				.withStringInput("field1").name("Field1")
//				.defaultValue("defaultField1Value")
//				.and().withStringInput("field2").name("Field2")
//				.and().withConfirmationInput("confirmation1").name("Confirmation1")
//				.and().withPathInput("path1").name("Path1")
//				.and().withSingleItemSelector("single1").name("Single1")
//				.selectItems(single1SelectItems).and()
//				.withMultiItemSelector("multi1").name("Multi1")
//				.selectItems(multi1SelectItems).and().build();
//		flow.run();
//
//	}
//
//	@ShellMethod(key = "run-flow")
//	public void runFlow() {
//		Map<String, String> single1SelectItems = new HashMap<>();
//		single1SelectItems.put("Field1", "field1");
//		single1SelectItems.put("Field2", "field2");
//		ComponentFlow flow = componentFlowBuilder.clone().reset()
//				.withSingleItemSelector("single1")
//				.name("Single1")
//				.selectItems(single1SelectItems)
//				.next(ctx -> ctx.getResultItem().get().getItem())
//				.and()
//				.withStringInput("field1")
//				.name("Field1")
//				.defaultValue("defaultField1Value")
//				.next(ctx -> null)
//				.and()
//				.withStringInput("field2")
//				.name("Field2")
//				.defaultValue("defaultField2Value")
//				.next(ctx -> null)
//				.and()
//				.build();
//		flow.run();
//	}
//
//	@ShellMethod(key = "flow showcase2", value = "Showcase with options", group = "Flow")
//	public String showcase2(
//			@ShellOption(help = "Field1 value", defaultValue = ShellOption.NULL) String field1,
//			@ShellOption(help = "Field2 value", defaultValue = ShellOption.NULL) String field2,
//			@ShellOption(help = "Confirmation1 value", defaultValue = ShellOption.NULL) Boolean confirmation1,
//			@ShellOption(help = "Path1 value", defaultValue = ShellOption.NULL) String path1,
//			@ShellOption(help = "Single1 value", defaultValue = ShellOption.NULL) String single1,
//			@ShellOption(help = "Multi1 value", defaultValue = ShellOption.NULL) List<String> multi1
//	) {
//		Map<String, String> single1SelectItems = new HashMap<>();
//		single1SelectItems.put("key1", "value1");
//		single1SelectItems.put("key2", "value2");
//		List<SelectItem> multi1SelectItems = Arrays.asList(SelectItem.of("key1", "value1"),
//				SelectItem.of("key2", "value2"), SelectItem.of("key3", "value3"));
//		List<String> multi1ResultValues = multi1 != null ? multi1 : new ArrayList<>();
//		ComponentFlow flow = componentFlowBuilder.clone().reset()
//				.withStringInput("field1")
//				.name("Field1")
//				.defaultValue("defaultField1Value")
//				.resultValue(field1)
//				.resultMode(ResultMode.VERIFY)
//				.and()
//				.withStringInput("field2")
//				.name("Field2")
//				.resultValue(field2)
//				.resultMode(ResultMode.ACCEPT)
//				.and()
//				.withConfirmationInput("confirmation1")
//				.name("Confirmation1")
//				.resultValue(confirmation1)
//				.resultMode(ResultMode.ACCEPT)
//				.and()
//				.withPathInput("path1")
//				.name("Path1")
//				.resultValue(path1)
//				.resultMode(ResultMode.ACCEPT)
//				.and()
//				.withSingleItemSelector("single1")
//				.name("Single1")
//				.selectItems(single1SelectItems)
//				.resultValue(single1)
//				.resultMode(ResultMode.ACCEPT)
//				.and()
//				.withMultiItemSelector("multi1")
//				.name("Multi1")
//				.selectItems(multi1SelectItems)
//				.resultValues(multi1ResultValues)
//				.resultMode(ResultMode.ACCEPT)
//				.and()
//				.build();
//		ComponentFlowResult result = flow.run();
//		StringBuilder buf = new StringBuilder();
//		result.getContext().stream().forEach(e -> {
//			buf.append(e.getKey());
//			buf.append(" = ");
//			buf.append(e.getValue());
//			buf.append("\n");
//		});
//		return buf.toString();
//	}

}
