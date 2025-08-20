package com.br.component;

import com.br.conta.ContaFrame;
import com.br.despesa.DespesaFrame;
import com.br.fornecedor.FornecedorFrame;
import com.br.shared.DefaultInternalFrame;
import com.br.shared.MIContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static com.br.component.ContextMenu.*;

public class DesktopPane extends JDesktopPane implements ActionListener {

    public DesktopPane() {
        super();
        this.init();
    }

    public void init(){
        this.setComponentPopupMenu(new ContextMenu(this));
    }

    public void exit(){
        int resposta = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?","Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if(resposta == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof MIContext){
            switch (e.getActionCommand()){
                case DESPESA_CMD: openFrame(new DespesaFrame()); break;
                case CONTA_CMD: openFrame(new ContaFrame()); break;
                case FORNECEDOR_CMD: openFrame(new FornecedorFrame()); break;
                case SAIR_CMD: exit(); break;
            }
        }
    }

    public void openFrame(DefaultInternalFrame defaultInternalFrame){
        Random random = new Random();
        int x = random.nextInt(0, (getWidth()/4));
        int y = random.nextInt(0, (getHeight()/4));
        defaultInternalFrame.setLocation(x,y);
        add(defaultInternalFrame);
        defaultInternalFrame.moveToFront();;
    }

}
