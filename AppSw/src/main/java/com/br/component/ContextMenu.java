package com.br.component;

import com.br.shared.MIContext;
import jiconfont.icons.font_awesome.FontAwesome;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ContextMenu extends JPopupMenu {
    public static final String  DESPESA_CMD = "despesa";
    public static final String  CONTA_CMD = "conta";
    public static final String  FORNECEDOR_CMD = "fornecedor";
    public static final String  SAIR_CMD = "sair";

    public ContextMenu(ActionListener al) {
        super();

        MIContext despesaItem = new MIContext("Despesas", DESPESA_CMD, al, FontAwesome.MONEY);
        MIContext contaItem = new MIContext("Contas", CONTA_CMD, al, FontAwesome.BARCODE);
        MIContext fornecedorItem = new MIContext("Fornecedores", FORNECEDOR_CMD, al, FontAwesome.DROPBOX);
        MIContext exitItem = new MIContext("Sair", SAIR_CMD, al, FontAwesome.POWER_OFF);

        add(despesaItem);
        add(contaItem);
        add(fornecedorItem);
        add(exitItem);

    }
}
