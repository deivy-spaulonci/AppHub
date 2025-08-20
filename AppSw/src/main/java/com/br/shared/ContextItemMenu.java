package com.br.shared;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ContextItemMenu extends JMenuItem {

    public ContextItemMenu(){}

    public ContextItemMenu(String text,
                           FontAwesome fontAwesome,
                           ActionListener actionListener){
        super(text);
        if(fontAwesome!=null){
            Icon icon = IconFontSwing.buildIcon(fontAwesome, 15,  Color.lightGray);
            setIcon(icon);
        }
        addActionListener(actionListener);
    }

    public ContextItemMenu(String text, FontAwesome fontAwesome){
        super(text);
        if(fontAwesome!=null){
            Icon icon = IconFontSwing.buildIcon(fontAwesome, 15,  Color.lightGray);
            setIcon(icon);
        }
    }


    public ContextItemMenu itemNovo() {
        return new ContextItemMenu("Novo", FontAwesome.PLUS_SQUARE_O);
    }

    public ContextItemMenu itemEditar() {
        return new ContextItemMenu("Editar", FontAwesome.PENCIL_SQUARE_O);
    }

    public ContextItemMenu itemExcluir() {
        return new ContextItemMenu("Excluir", FontAwesome.TIMES_CIRCLE);
    }
}
