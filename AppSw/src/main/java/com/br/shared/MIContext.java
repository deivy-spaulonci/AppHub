package com.br.shared;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MIContext extends JMenuItem{
    public MIContext(String text, String actionCommand, ActionListener al, FontAwesome fontAwesome) {
        super(text);
        setActionCommand(actionCommand);
        addActionListener(al);
        if(fontAwesome!=null){
            Icon icon = IconFontSwing.buildIcon(fontAwesome, 15,  Color.lightGray);
            setIcon(icon);
        }
    }
}
