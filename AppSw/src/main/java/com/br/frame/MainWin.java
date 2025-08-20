package com.br.frame;

import com.br.component.DesktopPane;
import com.br.dao.generic.ConnectionFactory;
import com.br.util.Util;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


public class MainWin extends JFrame {
    private DesktopPane desktop = new DesktopPane();

    public MainWin() {
        setSize(2600, 1600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
        setVisible(true);
        setTitle("App Swing Window");
    }

    public void init(){

        getContentPane().add(desktop);

        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão obtida com sucesso!");
        } catch (SQLException e) {
            Util.alertErro(this,"Erro na onexão com o banco!");
            e.printStackTrace();
        }
    }
}
