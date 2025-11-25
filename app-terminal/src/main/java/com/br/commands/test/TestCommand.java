package com.br.commands.test;

import com.br.config.ShellHelper;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TestCommand {

    private ShellHelper shellHelper;

    public TestCommand(ShellHelper shellHelper) {
        this.shellHelper = shellHelper;
    }

    @ShellMethod("funcao de teste")
    public void teste() {

    }
}
