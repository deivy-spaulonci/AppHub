package com.br.shared;

import com.br.dao.FornecedorDAO;
import com.br.entity.Fornecedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AutoCompleteFornecedor extends JComboBox {

    private FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public AutoCompleteFornecedor() {
        init();
    }

    public void init(){
        setEditable(true);

        JTextField textField = (JTextField) getEditor().getEditorComponent();
        textField.setMargin(new Insets(3,3,3,3));
        textField.setColumns(30);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> comboFilter(textField.getText()));
            }
        });
    }

    public void comboFilter(String enteredText) {
        ArrayList<Fornecedor> retorno = new ArrayList<>(fornecedorDAO.getFornecedorByFilter(enteredText));
        if (!retorno.isEmpty()) {
            ArrayList<FornecedorCbx> fornecedorCbxList = new ArrayList<>();
            retorno.forEach(fornecedor -> {
                FornecedorCbx fornecedorCbx = new FornecedorCbx(fornecedor);
                fornecedorCbxList.add(fornecedorCbx);
            });
            this.setModel(new DefaultComboBoxModel(fornecedorCbxList.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        }
        else {
            this.hidePopup();
        }
    }
}