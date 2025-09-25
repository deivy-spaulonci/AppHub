package com.br.commands.test;

import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.loading.ProgressBar;
import com.br.loading.ProgressCounter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.concurrent.CountDownLatch;

@ShellComponent
public class TestCommand  extends AbstractShellComponent {

    private Terminal terminal;
    private ShellHelper shellHelper;
    private DefaultComponent defaultComponent;
    private ProgressCounter progressCounter;
    private ProgressBar progressBar;

    @Autowired
    public TestCommand(Terminal terminal,
                       ShellHelper shellHelper,
                       ProgressCounter progressCounter,
                       ProgressBar progressBar) {
        this.progressCounter = progressCounter;
        this.terminal = terminal;
        this.shellHelper = shellHelper;
        this.progressBar = progressBar;
    }

    @ShellMethod
    public void teste() throws IOException, InterruptedException {
        // Unicode: U+2580..U+259F são blocos
        // Unicode: U+2500..U+257F são "box drawing"
//        System.out.println("Blocos:");
//        for (int code = 0x2580; code <= 0x259F; code++) {
//            System.out.printf("U+%04X: %c%n", code, (char) code);
//        }
//
//        System.out.println("\nBox Drawing:");
//        for (int code = 0x2500; code <= 0x257F; code++) {
//            System.out.printf("U+%04X: %c%n", code, (char) code);
//        }

    }
}
