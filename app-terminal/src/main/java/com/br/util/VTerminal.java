package com.br.util;

import com.br.config.ShellHelper;

public class VTerminal {

    private ShellHelper shellHelper;

    public VTerminal(ShellHelper shellHelper) {
        this.shellHelper = shellHelper;
    }

    public void msgErro(String mensagem){
        shellHelper.prError(String.format("%n " + '⛔' + "  "+mensagem +" ! %n"));
    }

    public void msgWarn(String mensagem){
        shellHelper.prWarning(String.format("%n " + '⚠' + "  "+mensagem +" ! %n"));
    }

    public void msgSucess(String mensagem){
        shellHelper.prSuccess(String.format("%n   "+ '✅' + "  "+mensagem +" ! %n"));
    }

    public void msg(String mensagem){
        shellHelper.prInfo(mensagem);
    }

    public void lnLabelValue(String label, String msg){
        shellHelper.prInfo("| %-16s : %-25s".formatted(label, msg));
    }

    public void lnDefault(int larg){
        shellHelper.prInfo("+"+"-".repeat(larg));
    }

    public void removeLn(){
        System.out.print("\033[F\033[K");
    }

    public void clear(){
        System.out.print("\033\143");
    }
}
