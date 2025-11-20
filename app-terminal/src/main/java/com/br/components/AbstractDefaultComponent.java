package com.br.components;

import com.br.config.ShellHelper;
import lombok.Getter;
import org.jline.terminal.Terminal;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.style.TemplateExecutor;

@Getter
public abstract class  AbstractDefaultComponent {

    protected final Terminal terminal;
    protected final TemplateExecutor templateExecutor;
    protected final ResourceLoader resourceLoader;
    protected final ShellHelper shellHelper;
    protected final ComponentFlow.Builder componentFlowBuilder;

    public AbstractDefaultComponent(ComponentFlow.Builder componentFlowBuilder,
                            Terminal terminal,
                            TemplateExecutor templateExecutor,
                            ResourceLoader resourceLoader,
                            ShellHelper shellHelper){
        this.componentFlowBuilder = componentFlowBuilder;
        this.terminal = terminal;
        this.templateExecutor = templateExecutor;
        this.resourceLoader = resourceLoader;
        this.shellHelper = shellHelper;
    }
}
