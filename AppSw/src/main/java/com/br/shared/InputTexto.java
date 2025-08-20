package com.br.shared;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit;

public class InputTexto extends JTextField{

    private JMenuItem copia = new JMenuItem(new DefaultEditorKit.CopyAction());
    private JMenuItem recorta = new JMenuItem(new DefaultEditorKit.CutAction());
    private JMenuItem colar = new JMenuItem(new DefaultEditorKit.PasteAction());
    
    public InputTexto(int columns) {
        super(columns);
        setBorder(new EmptyBorder(3,3,3,3));
        JPopupMenu popupmenu = new JPopupMenu();
        copia.setText("Copiar");
        recorta.setText("Recortar");
        colar.setText("Colar");
        popupmenu.add(copia);
        popupmenu.add(recorta);
        popupmenu.add(colar);
        setComponentPopupMenu(popupmenu);
    }    
}
