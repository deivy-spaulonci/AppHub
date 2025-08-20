package com.br.teste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleTerminal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleTerminal::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Simple Terminal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Área de texto para exibir a saída do terminal
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Campo de texto para entrada de comandos
        JTextField commandField = new JTextField();
        frame.add(commandField, BorderLayout.SOUTH);

        // Ação ao pressionar Enter
        commandField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commandField.getText();
                textArea.append(">> " + command + "\n");
                commandField.setText("");

                // Processar o comando aqui
                // Exemplo: apenas ecoar o comando
                // Você pode adicionar lógica para executar comandos de sistema, se necessário
                textArea.append("Você digitou: " + command + "\n");
            }
        });

        frame.setVisible(true);
    }
}