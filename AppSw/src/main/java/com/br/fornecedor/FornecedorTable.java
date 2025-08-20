package com.br.fornecedor;

import com.br.dao.FornecedorDAO;
import com.br.entity.Fornecedor;
import com.br.shared.Botao;
import com.br.shared.ContextItemMenu;
import com.br.shared.LoadingDialog;
import jiconfont.icons.font_awesome.FontAwesome;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

public class FornecedorTable extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo = new DefaultTableModel();
    private JPopupMenu popupmenu;
    private FornecedorFrame fornecedorFrame;
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private JLabel qtdLabel = new JLabel("", JLabel.RIGHT);
    private JTextField inputBusca = new JTextField(30);
    private Botao btBusca = new Botao().botaoPesquisa();

    public FornecedorTable(FornecedorFrame fornecedorFrame) {
        this.fornecedorFrame = fornecedorFrame;
        setLayout(new BorderLayout());
        init();
    }

    public void init(){
        criaTabela();
        pesquisar(null);
        qtdLabel.setBorder(new EmptyBorder(3, 3, 3, 3));
        btBusca.addActionListener(al -> pesquisar(inputBusca.getText()));
        JPanel panelBusca = new JPanel();
        panelBusca.add(inputBusca);
        panelBusca.add(btBusca);
        add(panelBusca, BorderLayout.NORTH);
        add(qtdLabel, BorderLayout.SOUTH);

    }

    public void criaTabela(){
        tabela = new JTable(modelo);
        modelo.setColumnIdentifiers(new Object[]{"Id", "Nome", "Razão Social", "CNPJ", "CPF"});
        tabela.getColumnModel().getColumn(0).setMaxWidth(60);
        tabela.getColumnModel().getColumn(3).setMaxWidth(180);
        tabela.getColumnModel().getColumn(3).setMinWidth(180);
        tabela.getColumnModel().getColumn(4).setMaxWidth(130);
        tabela.getColumnModel().getColumn(4).setMinWidth(130);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelo);
        sorter.setComparator(0, new Comparator<Long>(){
            public int compare(Long d1, Long d2) {
                return d1.compareTo(d2);
            }
        });
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    editar();
                }
            }
        });
        tabela.setRowSelectionAllowed(false);
        popupmenu = new JPopupMenu();
        ContextItemMenu novo = new ContextItemMenu().itemNovo();
        ContextItemMenu edit = new ContextItemMenu().itemEditar();
        ContextItemMenu excl = new ContextItemMenu().itemExcluir();

        popupmenu.add(novo);
        popupmenu.add(edit);
        popupmenu.add(excl);

        tabela.setComponentPopupMenu(popupmenu);
        tabela.setRowSorter(sorter);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void pesquisar(String texto) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog loadingDialog = new LoadingDialog(parentFrame);

        SwingWorker<Void, Void> sw = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    modelo.setNumRows(0);

                    System.out.println(texto);

                    ArrayList<Fornecedor> retorno = new ArrayList<Fornecedor>(
                            fornecedorDAO.getFornecedorByFilter(texto));

                    if(!retorno.isEmpty())
                        retorno.forEach(f ->{
                            modelo.addRow(new Object[]{f.getId(),
                                    f.getNome(),
                                    f.getRazaoSocial(),
                                    f.getCnpj(),
                                    f.getCpf()});
                        });
                    qtdLabel.setText("Registros: " + retorno.size()+"");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.closeLoading();
            }
        };
        sw.execute();
        loadingDialog.showLoading();
    }

    public void editar(){
        javax.swing.JOptionPane.showMessageDialog(null, "Teste");
    }
}
