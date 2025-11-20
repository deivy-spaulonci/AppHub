package com.br.config;


import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShellHelper {
    @Value("${shell.out.info}")
    public String infoColor;
    @Value("${shell.out.success}")
    public String successColor;
    @Value("${shell.out.warning}")
    public String warningColor;
    @Value("${shell.out.error}")
    public String errorColor;
    @Setter
    @Getter
    private Terminal terminal;

    @Autowired
    public ShellHelper(Terminal terminal) {
        this.terminal = terminal;
    }
    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }
    public String getInfoMessage(String message) {
        return getColored(message, PromptColor.valueOf(infoColor));
    }
    public String getSuccessMessage(String message) {
        return getColored(message, PromptColor.valueOf(successColor));
    }
    public String getWarningMessage(String message) {
        return getColored(message, PromptColor.valueOf(warningColor));
    }
    public String getErrorMessage(String message) {
        return getColored(message, PromptColor.valueOf(errorColor));
    }

    public void pr(String message) {
        pr(message, null);
    }
    public void prSuccess(String message) {
        pr(message, PromptColor.valueOf(successColor));
    }
    public void prInfo(String message) {
        pr(message, PromptColor.valueOf(infoColor));
    }
    public void prWarning(String message) {
        pr(message, PromptColor.valueOf(warningColor));
    }
    public void prError(String message) {
        pr(message, PromptColor.valueOf(errorColor));
    }
    public void pr(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }
    //--- set / get methods ---------------------------------------------------

}
