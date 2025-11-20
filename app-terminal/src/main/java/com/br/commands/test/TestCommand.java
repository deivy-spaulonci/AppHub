package com.br.commands.test;

import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.loading.ProgressBar;
import com.br.loading.ProgressCounter;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ShellComponent
public class TestCommand {

    private ShellHelper shellHelper;
    private DefaultComponent defaultComponent;
    private ProgressCounter progressCounter;
    private ProgressBar progressBar;

    @Autowired
    public TestCommand(ShellHelper shellHelper,
                       ProgressCounter progressCounter,
                       ProgressBar progressBar) {
        this.progressCounter = progressCounter;
        this.shellHelper = shellHelper;
        this.progressBar = progressBar;
    }

    @ShellMethod("apenas teste")
    public void teste() throws IOException, InterruptedException {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setPreferTerminalEmulator(true);
        terminalFactory.setForceTextTerminal(true);

        Terminal terminal = terminalFactory.createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
                new DefaultWindowManager(),
                new EmptySpace(TextColor.Indexed.fromRGB(0,0,0)));

        BasicWindow window = new BasicWindow();

        MenuBar menubar = new MenuBar();
        Menu menuFile = new Menu("File");
        menubar.add(menuFile);
        MenuItem menuItem1 = new MenuItem("Open", () -> {
            File file = new FileDialogBuilder().build().showDialog(gui);
            if (file != null)
                MessageDialog.showMessageDialog(gui, "Open", "Selected file:\n" + file, MessageDialogButton.OK);
        });
        MenuItem menuItem2 = new MenuItem("teste", () -> showCombo(window));
        MenuItem menuItem3 = new MenuItem("Exit", () -> System.exit(0));
        menuFile.add(menuItem1);
        menuFile.add(menuItem2);
        menuFile.add(menuItem3);

        // "Help" menu
        Menu menuHelp = new Menu("Help");
        menubar.add(menuHelp);
        MenuItem menuItem4 = new MenuItem("Homepage", () -> MessageDialog.showMessageDialog(gui, "Homepage", "https://github.com/mabe02/lanterna", MessageDialogButton.OK));
        MenuItem menuItem5 = new MenuItem("About", () -> MessageDialog.showMessageDialog(gui, "About", "Lanterna drop-down menu", MessageDialogButton.OK));
        menuHelp.add(menuItem4);
        menuHelp.add(menuItem5);

        window.setComponent(menubar);

        //LanternaThemes.getRegisteredThemes().add("my");
        //gui.setTheme(LanternaThemes.getRegisteredTheme("blaster"));
        gui.setTheme(new MyTheme());
        gui.addWindowAndWait(window);
    }

    public void showCombo(BasicWindow window) {
        Panel mainPanel = new Panel();
        window.setComponent(mainPanel);
    }
}