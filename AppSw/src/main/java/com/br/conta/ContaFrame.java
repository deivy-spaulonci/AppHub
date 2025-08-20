package com.br.conta;

import com.br.shared.DefaultInternalFrame;

import java.awt.*;

public class ContaFrame extends DefaultInternalFrame {

    public ContaFrame() {
        super("Conta", 1600,1024);
        setLayout(new BorderLayout(3,3));
        init();
        show();
    }

    public void init() {

    }
}
