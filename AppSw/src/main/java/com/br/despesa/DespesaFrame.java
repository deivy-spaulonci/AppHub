package com.br.despesa;

import com.br.shared.DefaultInternalFrame;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DespesaFrame extends DefaultInternalFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private DespesaTable despesaTable;

    public DespesaFrame() {
        super("Despesa", 1600,1024);
        setLayout(new BorderLayout(3,3));
        init();
        show();
    }

    public void init(){
        despesaTable = new DespesaTable(this);
        Icon iconAdd = IconFontSwing.buildIcon(FontAwesome.PLUS_SQUARE_O, 15, Color.lightGray);
        Icon iconSea = IconFontSwing.buildIcon(FontAwesome.SEARCH, 15, Color.lightGray);
        DespesaSearch despesaSearch = new DespesaSearch(despesaTable);
        tabbedPane.addTab("Cadastro", iconAdd, new DespesaForm(despesaSearch));
        tabbedPane.addTab("Filtros", iconSea, new DespesaSearch(despesaTable));
        add(tabbedPane, BorderLayout.NORTH);
        add(despesaTable, BorderLayout.CENTER);

        teste();
    }

    public void teste(){
        JPanel panel = new JPanel();

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Nome");
        model.addColumn("Valor");
        model.addColumn("Data");

        model.addRow(new Object[]{"","",""});
        model.addRow(new Object[]{"","",""});
        model.addRow(new Object[]{"","",""});

        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));

        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        tabbedPane.add("apenas teste", panel);

    }
}
