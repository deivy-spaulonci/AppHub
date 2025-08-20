package com.br;

import com.br.frame.MainWin;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.*;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        SwingUtilities.invokeLater(() -> {
            FlatDarkLaf.setup();
            IconFontSwing.register(FontAwesome.getIconFont());
            new MainWin();
        });
    }
}