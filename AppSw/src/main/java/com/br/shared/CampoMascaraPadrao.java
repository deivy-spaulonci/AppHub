package com.br.shared;

import java.awt.*;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class CampoMascaraPadrao  extends JFormattedTextField{

    public CampoMascaraPadrao(String mascara, int colunas) {
        setColumns(colunas);
        setBorder(new EmptyBorder(3,3,3,3));
        setMargin(new Insets(3,3,3,3));
        try {
            MaskFormatter mascaradata = new MaskFormatter(mascara);
            setFormatterFactory(new DefaultFormatterFactory(mascaradata));
            setHorizontalAlignment(JFormattedTextField.CENTER);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

}
