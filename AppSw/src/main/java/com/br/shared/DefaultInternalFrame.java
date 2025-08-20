package com.br.shared;

import javax.swing.*;

public class DefaultInternalFrame extends JInternalFrame {
    public DefaultInternalFrame(String title, int width, int height) {
        setTitle(title);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(width, height);
        setBounds(10,10,width,height);
    }
}
