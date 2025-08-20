package com.br.shared;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ComboTipo<T> extends JComboBox<T> {

    private DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

    public ComboTipo(Collection<T> list, boolean valueNUll) {
        super();

        setBorder(BorderFactory.createEmptyBorder());

        JTextField textField = (JTextField) getEditor().getEditorComponent();
        textField.setMargin(new Insets(3,3,3,3));
        setPreferredSize(new Dimension(250,28));
        if(valueNUll)
            model.addElement(null);
        list.forEach(item -> model.addElement((T) item));
        this.setModel(model);
    }
}
