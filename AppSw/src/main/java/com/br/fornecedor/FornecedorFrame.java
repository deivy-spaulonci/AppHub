package com.br.fornecedor;

import com.br.entity.Fornecedor;
import com.br.shared.DefaultInternalFrame;

import java.awt.*;

public class FornecedorFrame extends DefaultInternalFrame {

    private FornecedorTable fornecedorTable;

    public FornecedorFrame() {
        super("Fornecedor", 1600,1024);
        setLayout(new BorderLayout(3,3));
        init();
        show();
    }

    public void init() {
        fornecedorTable = new FornecedorTable(this);

        add(new FornecedorForm(), BorderLayout.NORTH);
        add(fornecedorTable, BorderLayout.CENTER);
    }
}
