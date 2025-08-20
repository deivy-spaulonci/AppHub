package com.br.despesa;

import com.br.dao.DespesaDAO;
import com.br.dao.FormaPagamentoDAO;
import com.br.dao.TipoDespesaDAO;
import com.br.entity.Despesa;
import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import com.br.shared.*;
import com.br.util.Util;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class DespesaForm extends JPanel {

    private DespesaDAO despesaDAO =  new DespesaDAO();
    private TipoDespesaDAO tipoDespesaDAO = new TipoDespesaDAO();
    private FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO();
    private ComboTipo comboTipo;
    private AutoCompleteFornecedor autoCompleteFornecedor;
    private InputData inputData = new InputData();
    private ComboTipo comboForma;
    private InputMoeda inputValor = new InputMoeda(2,10);
    private InputTexto inputObs = new InputTexto(50);
    private Botao salvar;
    private DespesaSearch despesaSearch;

    public DespesaForm(DespesaSearch despesaSearch) {
        this.despesaSearch = despesaSearch;
        init();
    }

    public void init(){
        autoCompleteFornecedor = new AutoCompleteFornecedor();
        comboTipo = new ComboTipo(tipoDespesaDAO.getTipoDespesa(), false);
        comboForma = new ComboTipo(formaPagamentoDAO.getFormaPagamento(), false);
        salvar = new Botao().botaoSalvar();
        salvar.addActionListener(a -> salvar());

        setLayout(new MigLayout());

        add(new JLabel("Despesa: "));
        add(comboTipo, "wrap");
        add(new JLabel("Fornecedor: "));
        add(autoCompleteFornecedor,"wrap");
        add(new JLabel("Data: "));
        add(inputData,"wrap");
        add(new JLabel("Forma Pgto: "));
        add(comboForma, "wrap");
        add(new JLabel("Valor: "));
        add(inputValor, "wrap");
        add(new JLabel("Obs: "));
        add(inputObs, "wrap");
        add(new JLabel(""));
        add(salvar);
    }

    public void salvar(){
        FornecedorCbx fornecedorCbx = (FornecedorCbx) autoCompleteFornecedor.getSelectedItem();
        if(fornecedorCbx == null || fornecedorCbx.getFornecedor()==null || fornecedorCbx.getFornecedor().getId()==null){
            Util.alertErro(this, "Fornecedor invalido!");
        }else if(inputData.dataInvalidaVazia()){
            Util.alertErro(this, "Data invalido!");
        }else if(inputValor.valorInvalido()){
            Util.alertErro(this, "Vaalor invalido!");
        }else{

            final Despesa despesa = Despesa.builder()
                    .tipoDespesa((TipoDespesa) comboTipo.getSelectedItem())
                    .fornecedor(fornecedorCbx.getFornecedor())
                    .dataPagamento(inputData.getDateValor())
                    .formaPagamento((FormaPagamento) comboForma.getSelectedItem())
                    .valor(inputValor.getValue())
                    .obs(inputObs.getText())
                    .build();

            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            LoadingDialog loadingDialog = new LoadingDialog(parentFrame);

            SwingWorker<Void, Void> sw = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        despesaDAO.inserir(despesa);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void done() {
                    reset();
                    despesaSearch.search();
                    loadingDialog.closeLoading();
                }
            };
            sw.execute();
            loadingDialog.showLoading();
        }

    }

    private void reset(){
        inputData.reset();
        inputValor.setText("0,00");
        inputObs.setText("");
    }
}
