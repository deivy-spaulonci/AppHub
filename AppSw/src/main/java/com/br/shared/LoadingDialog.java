package com.br.shared;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JDialog {
    public LoadingDialog(Frame parent) {
        super(parent, "Carregando...", true);
        setSize(300, 70);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // Modo indeterminado
        panel.add(progressBar, BorderLayout.CENTER);

        JLabel label = new JLabel("Carregando, por favor aguarde...", SwingConstants.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        add(panel);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public void showLoading() {
        setVisible(true);
    }

    public void closeLoading() {
        setVisible(false);
        dispose();
    }
}
