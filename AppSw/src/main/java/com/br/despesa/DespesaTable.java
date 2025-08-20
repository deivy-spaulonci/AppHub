package com.br.despesa;

import com.br.dao.DespesaDAO;
import com.br.entity.Despesa;
import com.br.filter.DespesaFilter;
import com.br.shared.ContextItemMenu;
import com.br.shared.LoadingDialog;
import com.br.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class DespesaTable extends JPanel {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JPopupMenu popupmenu;
    private BigDecimal total = BigDecimal.ZERO;
    private DespesaDAO despesaDAO = new DespesaDAO();
    private JLabel totalLabel = new JLabel("", JLabel.RIGHT);
    private DespesaFrame despesaFrame;

    public DespesaTable(DespesaFrame despesaFrame){
        this.despesaFrame = despesaFrame;
        setLayout(new BorderLayout());
        criaTablea();
        pesquisar(null);
        add(totalLabel, BorderLayout.SOUTH);
    }

    void criaTablea(){
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo){
            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int column = columnAtPoint(p);
                if (row >= 0 && column >= 0) {
                    return modelo.getValueAt(row, 6).toString();
                }
                return null;
            }
        };

        modelo.setColumnIdentifiers(new Object[]{"Id", "Tipo", "Fornecedor", "Data", "Forma Pagamento", "Valor", ""});
        tabela.getColumnModel().getColumn(0).setCellRenderer(Util.colunaCentralizada());
        tabela.getColumnModel().getColumn(3).setCellRenderer(Util.colunaData());
        tabela.getColumnModel().getColumn(5).setCellRenderer(Util.colunaValor());

        tabela.getColumnModel().getColumn(0).setMaxWidth(60);
        tabela.getColumnModel().getColumn(1).setMaxWidth(180);
        tabela.getColumnModel().getColumn(1).setMinWidth(180);
        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        tabela.getColumnModel().getColumn(3).setMinWidth(150);
        tabela.getColumnModel().getColumn(5).setMaxWidth(200);
        tabela.getColumnModel().getColumn(5).setMinWidth(150);
        //coluna observação
        tabela.getColumnModel().getColumn(6).setMinWidth(0);
        tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(0);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelo);
        sorter.setComparator(0, new Comparator<BigInteger>(){
            public int compare(BigInteger d1, BigInteger d2) {
                return d1.compareTo(d2);
            }
        });
        sorter.setComparator(3, new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                return o1.compareTo(o2);
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

//        MenuItemPadrao resultTipo = new MenuItemPadrao("Resultado por Despesa");
//
//        novo.addActionListener((e)->{novo();});
//        edit.addActionListener((e)->{editar();});
//        excl.addActionListener((e)->{excluir();});
//        resultTipo.addActionListener((e)->{criarResultdoPorDespesa();});

        popupmenu.add(novo);
        popupmenu.add(edit);
        popupmenu.add(excl);

//        popupmenu.add(resultTipo);

        tabela.setComponentPopupMenu(popupmenu);
        tabela.setRowSorter(sorter);


        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void pesquisar(DespesaFilter despesaFilter) {

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog loadingDialog = new LoadingDialog(parentFrame);

        SwingWorker<Void, Void> sw = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    modelo.setNumRows(0);
                    totalLabel.setText("");
                    total = BigDecimal.ZERO;
                    ArrayList<Despesa> retorno = new ArrayList<>(despesaDAO.getDespesasByFilter(despesaFilter));
                    if(!retorno.isEmpty())
                        retorno.forEach(d ->{
                            modelo.addRow(new Object[]{d.getId(),
                                    d.getTipoDespesa().getNome(),
                                    d.getFornecedor().getNome(),
                                    d.getDataPagamento(),
                                    d.getFormaPagamento().getNome(),
                                    d.getValor(),
                                    d.getObs()
                            });
                            total = total.add(d.getValor());
                        });
                    totalLabel.setText(Util.mf.format(total));
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
        var idSelecionado = Long.valueOf(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
        //despesaInternalFrame.editarSelecionado(idSelecionado);
    }
}
