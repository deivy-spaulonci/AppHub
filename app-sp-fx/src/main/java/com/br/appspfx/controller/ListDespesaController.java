package com.br.appspfx.controller;

import com.br.appspfx.service.DespesaService;
import com.br.appspfx.service.FormaPagamentoService;
import com.br.appspfx.service.FornecedorService;
import com.br.appspfx.service.TipoDespesaService;
import com.br.appspfx.util.Util;
import com.br.entity.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Component
public class ListDespesaController {

    @FXML
    public ComboBox<TipoDespesa> cbFilterTipo;
    @FXML
    public ComboBox<FormaPagamento> cbFilterForma;
    @FXML
    public ComboBox<Fornecedor> cbFilterFornec;
    @FXML
    public TextField dataFinal;
    @FXML
    public TextField dataInicial;


    private DespesaService despesaService;
    private TipoDespesaService tipoDespesaService;
    private FormaPagamentoService formaPagamentoService;
    private FornecedorService fornecedorService;
    private List<Despesa> despesas;

    private List<Fornecedor> fornecedores;

    @Lazy
    public ListDespesaController(DespesaService despesaService,
                                 TipoDespesaService tipoDespesaService,
                                 FormaPagamentoService formaPagamentoService,
                                 FornecedorService fornecedorService) {
        this.despesaService = despesaService;
        this.tipoDespesaService = tipoDespesaService;
        this.formaPagamentoService = formaPagamentoService;
        this.fornecedorService = fornecedorService;
    }
    @FXML
    private Label total;
    @FXML
    public Label registros;

    @FXML
    private TableView tableDespesa;

    @FXML
    private TableColumn<Despesa, BigInteger> idColumn;
    @FXML
    private TableColumn<Despesa, TipoDespesa> tipoColumn;
    @FXML
    private TableColumn<Despesa, LocalDate> dataColumn;
    @FXML
    private TableColumn<Despesa, Fornecedor> fornecColumn;
    @FXML
    private TableColumn<Despesa, BigDecimal> valorColumn;
    @FXML
    private TableColumn<Despesa, FormaPagamento> formaColumn;

    @FXML
    public void initialize() {
        // Configurar colunas
        idColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.ID));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.TIPO_DESPESA));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.DATA_PAGAMENTO));
        fornecColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.FORNECEDOR));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.VALOR));
        formaColumn.setCellValueFactory(new PropertyValueFactory<>(Despesa_.FORMA_PAGAMENTO));

        dataColumn.setCellFactory(column -> new TableCell<Despesa, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(Util.dfBR.format(item));
            }
        });

        valorColumn.setCellFactory(column -> new TableCell<Despesa, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if ((empty || item == null)) {
                    setText(null);
                } else {
                    setText(Util.mf.format(item));
                }
            }
        });

        despesas = despesaService.listarDespesa();
        BigDecimal resultado = despesas.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        total.setText("R$ "+ Util.mf.format(resultado));
        registros.setText("Registros: " + despesas.size());
        // Criar dados
        ObservableList<Despesa> data = FXCollections.observableArrayList(despesas);

        // Adicionar dados à TableView
        tableDespesa.setItems(data);

        cbFilterTipo.getItems().addAll(tipoDespesaService.listarTipoDespesas());
        cbFilterForma.getItems().addAll(formaPagamentoService.listarFormaPagamento());

        fornecedores = fornecedorService.findAll(null);
        cbFilterFornec.getItems().addAll(fornecedores);
        cbFilterFornec.setEditable(true);
        TextField textField = cbFilterFornec.getEditor();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFornecedores(newValue);
        });

        dataFinal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String formatted = formatDate(newValue);
                if (!formatted.equals(newValue)) {
                    dataFinal.setText(formatted);
                    dataFinal.positionCaret(formatted.length()); // Move o cursor para o final
                }
            }
        });

        dataInicial.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String formatted = formatDate(newValue);
                if (!formatted.equals(newValue)) {
                    dataInicial.setText(formatted);
                    dataInicial.positionCaret(formatted.length()); // Move o cursor para o final
                }
            }
        });

    }

    private String formatDate(String input) {
        String numbers = input.replaceAll("[^\\d]", "");
        // Formata a data
        StringBuilder formatted = new StringBuilder();
        if (numbers.length() > 0) {
            formatted.append(numbers.substring(0, Math.min(2, numbers.length()))); // Dia
        }
        if (numbers.length() >= 2) {
            formatted.append("/");
            formatted.append(numbers.substring(2, Math.min(4, numbers.length()))); // Mês
        }
        if (numbers.length() >= 4) {
            formatted.append("/");
            formatted.append(numbers.substring(4, Math.min(8, numbers.length()))); // Ano
        }
        return formatted.toString();
    }

    private void updateFornecedores(String input) {
        cbFilterFornec.getItems().clear();
        if (input.isEmpty()) {
            cbFilterFornec.getItems().addAll(fornecedores);
            return;
        }
        cbFilterFornec.getItems().addAll(fornecedorService.findAll(input));
        cbFilterFornec.show(); // Mostra a lista de sugestões
    }
}
