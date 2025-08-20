package com.br.commands;

import com.br.config.ShellHelper;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TestCommand  extends AbstractShellComponent {

    @Autowired
    private Terminal terminal;

    @Autowired
    private ShellHelper shellHelper;

    private DefaultComponent defaultComponent;

    @ShellMethod
    public void teste(){
        // Unicode: U+2580..U+259F são blocos
        // Unicode: U+2500..U+257F são "box drawing"
        System.out.println("Blocos:");
        for (int code = 0x2580; code <= 0x259F; code++) {
            System.out.printf("U+%04X: %c%n", code, (char) code);
        }

        System.out.println("\nBox Drawing:");
        for (int code = 0x2500; code <= 0x257F; code++) {
            System.out.printf("U+%04X: %c%n", code, (char) code);
        }
    }
}
