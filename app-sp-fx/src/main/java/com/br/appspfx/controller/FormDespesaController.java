package com.br.appspfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class FormDespesaController {
    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Opção 1", "Opção 2", "Opção 3");

        cbTipo.setOnAction(event -> {
            String selected = cbTipo.getSelectionModel().getSelectedItem();
        });
    }
}
