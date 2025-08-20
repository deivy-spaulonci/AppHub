package com.br.despesa;

import com.br.dao.FormaPagamentoDAO;
import com.br.dao.TipoDespesaDAO;
import com.br.entity.FormaPagamento;
import com.br.entity.Fornecedor;
import com.br.entity.TipoDespesa;
import com.br.filter.DespesaFilter;
import com.br.shared.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DespesaSearch extends JPanel {
    private TipoDespesaDAO tipoDespesaDAO = new TipoDespesaDAO();
    private FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO();
    private ComboTipo comboTipo;
    private AutoCompleteFornecedor autoCompleteFornecedor;
    private InputData inicio = new InputData();
    private InputData termino = new InputData();
    private ComboTipo comboForma;
    private Botao filtro;
    private Botao limpar;
    private DespesaTable despesaTable;

    public DespesaSearch(DespesaTable despesaTable) {
        init();
        this.despesaTable = despesaTable;
    }

    public void init() {
        autoCompleteFornecedor = new AutoCompleteFornecedor();
        comboTipo = new ComboTipo(tipoDespesaDAO.getTipoDespesa(), true);
        comboForma = new ComboTipo(formaPagamentoDAO.getFormaPagamento(), true);
        filtro = new Botao().botaoPesquisa();
        filtro.addActionListener(a -> search());
        limpar = new Botao().botaoLimpar();
        limpar.addActionListener(a -> limpar());

        setLayout(new MigLayout());

        add(new JLabel("Despesa: "));
        add(comboTipo, "wrap");
        add(new JLabel("Fornecedor: "));
        add(autoCompleteFornecedor, "wrap");
        add(new JLabel("Periodo: "));
        add(inicio, "split 2");
        add(termino,"wrap");
        add(new JLabel("Forma Pgto: "));
        add(comboForma, "wrap");

        add(new JLabel(""));
        add(filtro);
        add(limpar);
    }

    public void limpar(){
        comboTipo.setSelectedIndex(0);
        autoCompleteFornecedor.setSelectedItem(new FornecedorCbx(new Fornecedor()));
        inicio.reset();
        termino.reset();
        comboForma.setSelectedIndex(0);
    }

    public void search() {
        Fornecedor fornecedor = new Fornecedor();
        if(autoCompleteFornecedor.getSelectedItem()!=null)
            fornecedor = ((FornecedorCbx) autoCompleteFornecedor.getSelectedItem()).getFornecedor();
        DespesaFilter despesaFilter = DespesaFilter.builder()
                .tipoDespesa((TipoDespesa) comboTipo.getSelectedItem())
                .formaPagamento((FormaPagamento) comboForma.getSelectedItem())
                .fornecedor(fornecedor)
                .dataInicial(inicio.getDateValor())
                .dataFinal(termino.getDateValor())
                .build();

        despesaTable.pesquisar(despesaFilter);

    }
}
